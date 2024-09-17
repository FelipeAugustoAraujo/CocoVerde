package br.com.lisetech.cocoverde.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.domain.Frente;
import br.com.lisetech.cocoverde.domain.Produto;
import br.com.lisetech.cocoverde.domain.SaidaFinanceira;
import br.com.lisetech.cocoverde.repository.FrenteRepository;
import br.com.lisetech.cocoverde.service.dto.FrenteDTO;
import br.com.lisetech.cocoverde.service.mapper.FrenteMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FrenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FrenteResourceIT {

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;
    private static final Integer SMALLER_QUANTIDADE = 1 - 1;

    private static final Instant DEFAULT_CRIADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRIADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/frentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FrenteRepository frenteRepository;

    @Autowired
    private FrenteMapper frenteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFrenteMockMvc;

    private Frente frente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frente createEntity(EntityManager em) {
        Frente frente = new Frente().quantidade(DEFAULT_QUANTIDADE).criadoEm(DEFAULT_CRIADO_EM).modificadoEm(DEFAULT_MODIFICADO_EM);
        return frente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frente createUpdatedEntity(EntityManager em) {
        Frente frente = new Frente().quantidade(UPDATED_QUANTIDADE).criadoEm(UPDATED_CRIADO_EM).modificadoEm(UPDATED_MODIFICADO_EM);
        return frente;
    }

    @BeforeEach
    public void initTest() {
        frente = createEntity(em);
    }

    @Test
    @Transactional
    void createFrente() throws Exception {
        int databaseSizeBeforeCreate = frenteRepository.findAll().size();
        // Create the Frente
        FrenteDTO frenteDTO = frenteMapper.toDto(frente);
        restFrenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frenteDTO)))
            .andExpect(status().isCreated());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeCreate + 1);
        Frente testFrente = frenteList.get(frenteList.size() - 1);
        assertThat(testFrente.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testFrente.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
        assertThat(testFrente.getModificadoEm()).isEqualTo(DEFAULT_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void createFrenteWithExistingId() throws Exception {
        // Create the Frente with an existing ID
        frente.setId(1L);
        FrenteDTO frenteDTO = frenteMapper.toDto(frente);

        int databaseSizeBeforeCreate = frenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFrenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFrentes() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList
        restFrenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frente.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
            .andExpect(jsonPath("$.[*].modificadoEm").value(hasItem(DEFAULT_MODIFICADO_EM.toString())));
    }

    @Test
    @Transactional
    void getFrente() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get the frente
        restFrenteMockMvc
            .perform(get(ENTITY_API_URL_ID, frente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(frente.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.criadoEm").value(DEFAULT_CRIADO_EM.toString()))
            .andExpect(jsonPath("$.modificadoEm").value(DEFAULT_MODIFICADO_EM.toString()));
    }

    @Test
    @Transactional
    void getFrentesByIdFiltering() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        Long id = frente.getId();

        defaultFrenteShouldBeFound("id.equals=" + id);
        defaultFrenteShouldNotBeFound("id.notEquals=" + id);

        defaultFrenteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFrenteShouldNotBeFound("id.greaterThan=" + id);

        defaultFrenteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFrenteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFrentesByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where quantidade equals to DEFAULT_QUANTIDADE
        defaultFrenteShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the frenteList where quantidade equals to UPDATED_QUANTIDADE
        defaultFrenteShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllFrentesByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultFrenteShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the frenteList where quantidade equals to UPDATED_QUANTIDADE
        defaultFrenteShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllFrentesByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where quantidade is not null
        defaultFrenteShouldBeFound("quantidade.specified=true");

        // Get all the frenteList where quantidade is null
        defaultFrenteShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    void getAllFrentesByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultFrenteShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the frenteList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultFrenteShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllFrentesByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultFrenteShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the frenteList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultFrenteShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllFrentesByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where quantidade is less than DEFAULT_QUANTIDADE
        defaultFrenteShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the frenteList where quantidade is less than UPDATED_QUANTIDADE
        defaultFrenteShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllFrentesByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultFrenteShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the frenteList where quantidade is greater than SMALLER_QUANTIDADE
        defaultFrenteShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllFrentesByCriadoEmIsEqualToSomething() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where criadoEm equals to DEFAULT_CRIADO_EM
        defaultFrenteShouldBeFound("criadoEm.equals=" + DEFAULT_CRIADO_EM);

        // Get all the frenteList where criadoEm equals to UPDATED_CRIADO_EM
        defaultFrenteShouldNotBeFound("criadoEm.equals=" + UPDATED_CRIADO_EM);
    }

    @Test
    @Transactional
    void getAllFrentesByCriadoEmIsInShouldWork() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where criadoEm in DEFAULT_CRIADO_EM or UPDATED_CRIADO_EM
        defaultFrenteShouldBeFound("criadoEm.in=" + DEFAULT_CRIADO_EM + "," + UPDATED_CRIADO_EM);

        // Get all the frenteList where criadoEm equals to UPDATED_CRIADO_EM
        defaultFrenteShouldNotBeFound("criadoEm.in=" + UPDATED_CRIADO_EM);
    }

    @Test
    @Transactional
    void getAllFrentesByCriadoEmIsNullOrNotNull() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where criadoEm is not null
        defaultFrenteShouldBeFound("criadoEm.specified=true");

        // Get all the frenteList where criadoEm is null
        defaultFrenteShouldNotBeFound("criadoEm.specified=false");
    }

    @Test
    @Transactional
    void getAllFrentesByModificadoEmIsEqualToSomething() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where modificadoEm equals to DEFAULT_MODIFICADO_EM
        defaultFrenteShouldBeFound("modificadoEm.equals=" + DEFAULT_MODIFICADO_EM);

        // Get all the frenteList where modificadoEm equals to UPDATED_MODIFICADO_EM
        defaultFrenteShouldNotBeFound("modificadoEm.equals=" + UPDATED_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void getAllFrentesByModificadoEmIsInShouldWork() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where modificadoEm in DEFAULT_MODIFICADO_EM or UPDATED_MODIFICADO_EM
        defaultFrenteShouldBeFound("modificadoEm.in=" + DEFAULT_MODIFICADO_EM + "," + UPDATED_MODIFICADO_EM);

        // Get all the frenteList where modificadoEm equals to UPDATED_MODIFICADO_EM
        defaultFrenteShouldNotBeFound("modificadoEm.in=" + UPDATED_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void getAllFrentesByModificadoEmIsNullOrNotNull() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        // Get all the frenteList where modificadoEm is not null
        defaultFrenteShouldBeFound("modificadoEm.specified=true");

        // Get all the frenteList where modificadoEm is null
        defaultFrenteShouldNotBeFound("modificadoEm.specified=false");
    }

    @Test
    @Transactional
    void getAllFrentesByProdutoIsEqualToSomething() throws Exception {
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            frenteRepository.saveAndFlush(frente);
            produto = ProdutoResourceIT.createEntity(em);
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        em.persist(produto);
        em.flush();
        frente.addProduto(produto);
        frenteRepository.saveAndFlush(frente);
        Long produtoId = produto.getId();
        // Get all the frenteList where produto equals to produtoId
        defaultFrenteShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the frenteList where produto equals to (produtoId + 1)
        defaultFrenteShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }

    @Test
    @Transactional
    void getAllFrentesByEntradaFinanceiraIsEqualToSomething() throws Exception {
        EntradaFinanceira entradaFinanceira;
        if (TestUtil.findAll(em, EntradaFinanceira.class).isEmpty()) {
            frenteRepository.saveAndFlush(frente);
            entradaFinanceira = EntradaFinanceiraResourceIT.createEntity(em);
        } else {
            entradaFinanceira = TestUtil.findAll(em, EntradaFinanceira.class).get(0);
        }
        em.persist(entradaFinanceira);
        em.flush();
        frente.addEntradaFinanceira(entradaFinanceira);
        frenteRepository.saveAndFlush(frente);
        Long entradaFinanceiraId = entradaFinanceira.getId();
        // Get all the frenteList where entradaFinanceira equals to entradaFinanceiraId
        defaultFrenteShouldBeFound("entradaFinanceiraId.equals=" + entradaFinanceiraId);

        // Get all the frenteList where entradaFinanceira equals to (entradaFinanceiraId + 1)
        defaultFrenteShouldNotBeFound("entradaFinanceiraId.equals=" + (entradaFinanceiraId + 1));
    }

    @Test
    @Transactional
    void getAllFrentesBySaidaFinanceiraIsEqualToSomething() throws Exception {
        SaidaFinanceira saidaFinanceira;
        if (TestUtil.findAll(em, SaidaFinanceira.class).isEmpty()) {
            frenteRepository.saveAndFlush(frente);
            saidaFinanceira = SaidaFinanceiraResourceIT.createEntity(em);
        } else {
            saidaFinanceira = TestUtil.findAll(em, SaidaFinanceira.class).get(0);
        }
        em.persist(saidaFinanceira);
        em.flush();
        frente.addSaidaFinanceira(saidaFinanceira);
        frenteRepository.saveAndFlush(frente);
        Long saidaFinanceiraId = saidaFinanceira.getId();
        // Get all the frenteList where saidaFinanceira equals to saidaFinanceiraId
        defaultFrenteShouldBeFound("saidaFinanceiraId.equals=" + saidaFinanceiraId);

        // Get all the frenteList where saidaFinanceira equals to (saidaFinanceiraId + 1)
        defaultFrenteShouldNotBeFound("saidaFinanceiraId.equals=" + (saidaFinanceiraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFrenteShouldBeFound(String filter) throws Exception {
        restFrenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frente.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
            .andExpect(jsonPath("$.[*].modificadoEm").value(hasItem(DEFAULT_MODIFICADO_EM.toString())));

        // Check, that the count call also returns 1
        restFrenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFrenteShouldNotBeFound(String filter) throws Exception {
        restFrenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFrenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFrente() throws Exception {
        // Get the frente
        restFrenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFrente() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        int databaseSizeBeforeUpdate = frenteRepository.findAll().size();

        // Update the frente
        Frente updatedFrente = frenteRepository.findById(frente.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFrente are not directly saved in db
        em.detach(updatedFrente);
        updatedFrente.quantidade(UPDATED_QUANTIDADE).criadoEm(UPDATED_CRIADO_EM).modificadoEm(UPDATED_MODIFICADO_EM);
        FrenteDTO frenteDTO = frenteMapper.toDto(updatedFrente);

        restFrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeUpdate);
        Frente testFrente = frenteList.get(frenteList.size() - 1);
        assertThat(testFrente.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testFrente.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testFrente.getModificadoEm()).isEqualTo(UPDATED_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void putNonExistingFrente() throws Exception {
        int databaseSizeBeforeUpdate = frenteRepository.findAll().size();
        frente.setId(longCount.incrementAndGet());

        // Create the Frente
        FrenteDTO frenteDTO = frenteMapper.toDto(frente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFrente() throws Exception {
        int databaseSizeBeforeUpdate = frenteRepository.findAll().size();
        frente.setId(longCount.incrementAndGet());

        // Create the Frente
        FrenteDTO frenteDTO = frenteMapper.toDto(frente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFrente() throws Exception {
        int databaseSizeBeforeUpdate = frenteRepository.findAll().size();
        frente.setId(longCount.incrementAndGet());

        // Create the Frente
        FrenteDTO frenteDTO = frenteMapper.toDto(frente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frenteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFrenteWithPatch() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        int databaseSizeBeforeUpdate = frenteRepository.findAll().size();

        // Update the frente using partial update
        Frente partialUpdatedFrente = new Frente();
        partialUpdatedFrente.setId(frente.getId());

        restFrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFrente))
            )
            .andExpect(status().isOk());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeUpdate);
        Frente testFrente = frenteList.get(frenteList.size() - 1);
        assertThat(testFrente.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testFrente.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
        assertThat(testFrente.getModificadoEm()).isEqualTo(DEFAULT_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void fullUpdateFrenteWithPatch() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        int databaseSizeBeforeUpdate = frenteRepository.findAll().size();

        // Update the frente using partial update
        Frente partialUpdatedFrente = new Frente();
        partialUpdatedFrente.setId(frente.getId());

        partialUpdatedFrente.quantidade(UPDATED_QUANTIDADE).criadoEm(UPDATED_CRIADO_EM).modificadoEm(UPDATED_MODIFICADO_EM);

        restFrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFrente))
            )
            .andExpect(status().isOk());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeUpdate);
        Frente testFrente = frenteList.get(frenteList.size() - 1);
        assertThat(testFrente.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testFrente.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testFrente.getModificadoEm()).isEqualTo(UPDATED_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void patchNonExistingFrente() throws Exception {
        int databaseSizeBeforeUpdate = frenteRepository.findAll().size();
        frente.setId(longCount.incrementAndGet());

        // Create the Frente
        FrenteDTO frenteDTO = frenteMapper.toDto(frente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, frenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFrente() throws Exception {
        int databaseSizeBeforeUpdate = frenteRepository.findAll().size();
        frente.setId(longCount.incrementAndGet());

        // Create the Frente
        FrenteDTO frenteDTO = frenteMapper.toDto(frente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFrente() throws Exception {
        int databaseSizeBeforeUpdate = frenteRepository.findAll().size();
        frente.setId(longCount.incrementAndGet());

        // Create the Frente
        FrenteDTO frenteDTO = frenteMapper.toDto(frente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrenteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(frenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frente in the database
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFrente() throws Exception {
        // Initialize the database
        frenteRepository.saveAndFlush(frente);

        int databaseSizeBeforeDelete = frenteRepository.findAll().size();

        // Delete the frente
        restFrenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, frente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Frente> frenteList = frenteRepository.findAll();
        assertThat(frenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
