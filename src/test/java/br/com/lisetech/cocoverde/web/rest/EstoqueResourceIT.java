package br.com.lisetech.cocoverde.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.domain.Estoque;
import br.com.lisetech.cocoverde.domain.Produto;
import br.com.lisetech.cocoverde.domain.SaidaFinanceira;
import br.com.lisetech.cocoverde.repository.EstoqueRepository;
import br.com.lisetech.cocoverde.service.dto.EstoqueDTO;
import br.com.lisetech.cocoverde.service.mapper.EstoqueMapper;
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
 * Integration tests for the {@link EstoqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstoqueResourceIT {

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;
    private static final Integer SMALLER_QUANTIDADE = 1 - 1;

    private static final Instant DEFAULT_CRIADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRIADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/estoques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private EstoqueMapper estoqueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstoqueMockMvc;

    private Estoque estoque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estoque createEntity(EntityManager em) {
        Estoque estoque = new Estoque().quantidade(DEFAULT_QUANTIDADE).criadoEm(DEFAULT_CRIADO_EM).modificadoEm(DEFAULT_MODIFICADO_EM);
        return estoque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estoque createUpdatedEntity(EntityManager em) {
        Estoque estoque = new Estoque().quantidade(UPDATED_QUANTIDADE).criadoEm(UPDATED_CRIADO_EM).modificadoEm(UPDATED_MODIFICADO_EM);
        return estoque;
    }

    @BeforeEach
    public void initTest() {
        estoque = createEntity(em);
    }

    @Test
    @Transactional
    void createEstoque() throws Exception {
        int databaseSizeBeforeCreate = estoqueRepository.findAll().size();
        // Create the Estoque
        EstoqueDTO estoqueDTO = estoqueMapper.toDto(estoque);
        restEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estoqueDTO)))
            .andExpect(status().isCreated());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeCreate + 1);
        Estoque testEstoque = estoqueList.get(estoqueList.size() - 1);
        assertThat(testEstoque.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testEstoque.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
        assertThat(testEstoque.getModificadoEm()).isEqualTo(DEFAULT_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void createEstoqueWithExistingId() throws Exception {
        // Create the Estoque with an existing ID
        estoque.setId(1L);
        EstoqueDTO estoqueDTO = estoqueMapper.toDto(estoque);

        int databaseSizeBeforeCreate = estoqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estoqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstoques() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList
        restEstoqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estoque.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
            .andExpect(jsonPath("$.[*].modificadoEm").value(hasItem(DEFAULT_MODIFICADO_EM.toString())));
    }

    @Test
    @Transactional
    void getEstoque() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get the estoque
        restEstoqueMockMvc
            .perform(get(ENTITY_API_URL_ID, estoque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estoque.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.criadoEm").value(DEFAULT_CRIADO_EM.toString()))
            .andExpect(jsonPath("$.modificadoEm").value(DEFAULT_MODIFICADO_EM.toString()));
    }

    @Test
    @Transactional
    void getEstoquesByIdFiltering() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        Long id = estoque.getId();

        defaultEstoqueShouldBeFound("id.equals=" + id);
        defaultEstoqueShouldNotBeFound("id.notEquals=" + id);

        defaultEstoqueShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEstoqueShouldNotBeFound("id.greaterThan=" + id);

        defaultEstoqueShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEstoqueShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEstoquesByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where quantidade equals to DEFAULT_QUANTIDADE
        defaultEstoqueShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the estoqueList where quantidade equals to UPDATED_QUANTIDADE
        defaultEstoqueShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEstoquesByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultEstoqueShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the estoqueList where quantidade equals to UPDATED_QUANTIDADE
        defaultEstoqueShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEstoquesByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where quantidade is not null
        defaultEstoqueShouldBeFound("quantidade.specified=true");

        // Get all the estoqueList where quantidade is null
        defaultEstoqueShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    void getAllEstoquesByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultEstoqueShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the estoqueList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultEstoqueShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEstoquesByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultEstoqueShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the estoqueList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultEstoqueShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEstoquesByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where quantidade is less than DEFAULT_QUANTIDADE
        defaultEstoqueShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the estoqueList where quantidade is less than UPDATED_QUANTIDADE
        defaultEstoqueShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEstoquesByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultEstoqueShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the estoqueList where quantidade is greater than SMALLER_QUANTIDADE
        defaultEstoqueShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEstoquesByCriadoEmIsEqualToSomething() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where criadoEm equals to DEFAULT_CRIADO_EM
        defaultEstoqueShouldBeFound("criadoEm.equals=" + DEFAULT_CRIADO_EM);

        // Get all the estoqueList where criadoEm equals to UPDATED_CRIADO_EM
        defaultEstoqueShouldNotBeFound("criadoEm.equals=" + UPDATED_CRIADO_EM);
    }

    @Test
    @Transactional
    void getAllEstoquesByCriadoEmIsInShouldWork() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where criadoEm in DEFAULT_CRIADO_EM or UPDATED_CRIADO_EM
        defaultEstoqueShouldBeFound("criadoEm.in=" + DEFAULT_CRIADO_EM + "," + UPDATED_CRIADO_EM);

        // Get all the estoqueList where criadoEm equals to UPDATED_CRIADO_EM
        defaultEstoqueShouldNotBeFound("criadoEm.in=" + UPDATED_CRIADO_EM);
    }

    @Test
    @Transactional
    void getAllEstoquesByCriadoEmIsNullOrNotNull() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where criadoEm is not null
        defaultEstoqueShouldBeFound("criadoEm.specified=true");

        // Get all the estoqueList where criadoEm is null
        defaultEstoqueShouldNotBeFound("criadoEm.specified=false");
    }

    @Test
    @Transactional
    void getAllEstoquesByModificadoEmIsEqualToSomething() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where modificadoEm equals to DEFAULT_MODIFICADO_EM
        defaultEstoqueShouldBeFound("modificadoEm.equals=" + DEFAULT_MODIFICADO_EM);

        // Get all the estoqueList where modificadoEm equals to UPDATED_MODIFICADO_EM
        defaultEstoqueShouldNotBeFound("modificadoEm.equals=" + UPDATED_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void getAllEstoquesByModificadoEmIsInShouldWork() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where modificadoEm in DEFAULT_MODIFICADO_EM or UPDATED_MODIFICADO_EM
        defaultEstoqueShouldBeFound("modificadoEm.in=" + DEFAULT_MODIFICADO_EM + "," + UPDATED_MODIFICADO_EM);

        // Get all the estoqueList where modificadoEm equals to UPDATED_MODIFICADO_EM
        defaultEstoqueShouldNotBeFound("modificadoEm.in=" + UPDATED_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void getAllEstoquesByModificadoEmIsNullOrNotNull() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList where modificadoEm is not null
        defaultEstoqueShouldBeFound("modificadoEm.specified=true");

        // Get all the estoqueList where modificadoEm is null
        defaultEstoqueShouldNotBeFound("modificadoEm.specified=false");
    }

    @Test
    @Transactional
    void getAllEstoquesByProdutoIsEqualToSomething() throws Exception {
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            estoqueRepository.saveAndFlush(estoque);
            produto = ProdutoResourceIT.createEntity(em);
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        em.persist(produto);
        em.flush();
        estoque.addProduto(produto);
        estoqueRepository.saveAndFlush(estoque);
        Long produtoId = produto.getId();
        // Get all the estoqueList where produto equals to produtoId
        defaultEstoqueShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the estoqueList where produto equals to (produtoId + 1)
        defaultEstoqueShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }

    @Test
    @Transactional
    void getAllEstoquesByEntradaFinanceiraIsEqualToSomething() throws Exception {
        EntradaFinanceira entradaFinanceira;
        if (TestUtil.findAll(em, EntradaFinanceira.class).isEmpty()) {
            estoqueRepository.saveAndFlush(estoque);
            entradaFinanceira = EntradaFinanceiraResourceIT.createEntity(em);
        } else {
            entradaFinanceira = TestUtil.findAll(em, EntradaFinanceira.class).get(0);
        }
        em.persist(entradaFinanceira);
        em.flush();
        estoque.addEntradaFinanceira(entradaFinanceira);
        estoqueRepository.saveAndFlush(estoque);
        Long entradaFinanceiraId = entradaFinanceira.getId();
        // Get all the estoqueList where entradaFinanceira equals to entradaFinanceiraId
        defaultEstoqueShouldBeFound("entradaFinanceiraId.equals=" + entradaFinanceiraId);

        // Get all the estoqueList where entradaFinanceira equals to (entradaFinanceiraId + 1)
        defaultEstoqueShouldNotBeFound("entradaFinanceiraId.equals=" + (entradaFinanceiraId + 1));
    }

    @Test
    @Transactional
    void getAllEstoquesBySaidaFinanceiraIsEqualToSomething() throws Exception {
        SaidaFinanceira saidaFinanceira;
        if (TestUtil.findAll(em, SaidaFinanceira.class).isEmpty()) {
            estoqueRepository.saveAndFlush(estoque);
            saidaFinanceira = SaidaFinanceiraResourceIT.createEntity(em);
        } else {
            saidaFinanceira = TestUtil.findAll(em, SaidaFinanceira.class).get(0);
        }
        em.persist(saidaFinanceira);
        em.flush();
        estoque.addSaidaFinanceira(saidaFinanceira);
        estoqueRepository.saveAndFlush(estoque);
        Long saidaFinanceiraId = saidaFinanceira.getId();
        // Get all the estoqueList where saidaFinanceira equals to saidaFinanceiraId
        defaultEstoqueShouldBeFound("saidaFinanceiraId.equals=" + saidaFinanceiraId);

        // Get all the estoqueList where saidaFinanceira equals to (saidaFinanceiraId + 1)
        defaultEstoqueShouldNotBeFound("saidaFinanceiraId.equals=" + (saidaFinanceiraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstoqueShouldBeFound(String filter) throws Exception {
        restEstoqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estoque.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
            .andExpect(jsonPath("$.[*].modificadoEm").value(hasItem(DEFAULT_MODIFICADO_EM.toString())));

        // Check, that the count call also returns 1
        restEstoqueMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstoqueShouldNotBeFound(String filter) throws Exception {
        restEstoqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstoqueMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEstoque() throws Exception {
        // Get the estoque
        restEstoqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstoque() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();

        // Update the estoque
        Estoque updatedEstoque = estoqueRepository.findById(estoque.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEstoque are not directly saved in db
        em.detach(updatedEstoque);
        updatedEstoque.quantidade(UPDATED_QUANTIDADE).criadoEm(UPDATED_CRIADO_EM).modificadoEm(UPDATED_MODIFICADO_EM);
        EstoqueDTO estoqueDTO = estoqueMapper.toDto(updatedEstoque);

        restEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estoqueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estoqueDTO))
            )
            .andExpect(status().isOk());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
        Estoque testEstoque = estoqueList.get(estoqueList.size() - 1);
        assertThat(testEstoque.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testEstoque.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testEstoque.getModificadoEm()).isEqualTo(UPDATED_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void putNonExistingEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(longCount.incrementAndGet());

        // Create the Estoque
        EstoqueDTO estoqueDTO = estoqueMapper.toDto(estoque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estoqueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estoqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(longCount.incrementAndGet());

        // Create the Estoque
        EstoqueDTO estoqueDTO = estoqueMapper.toDto(estoque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estoqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(longCount.incrementAndGet());

        // Create the Estoque
        EstoqueDTO estoqueDTO = estoqueMapper.toDto(estoque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estoqueDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstoqueWithPatch() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();

        // Update the estoque using partial update
        Estoque partialUpdatedEstoque = new Estoque();
        partialUpdatedEstoque.setId(estoque.getId());

        partialUpdatedEstoque.quantidade(UPDATED_QUANTIDADE).criadoEm(UPDATED_CRIADO_EM);

        restEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstoque))
            )
            .andExpect(status().isOk());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
        Estoque testEstoque = estoqueList.get(estoqueList.size() - 1);
        assertThat(testEstoque.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testEstoque.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testEstoque.getModificadoEm()).isEqualTo(DEFAULT_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void fullUpdateEstoqueWithPatch() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();

        // Update the estoque using partial update
        Estoque partialUpdatedEstoque = new Estoque();
        partialUpdatedEstoque.setId(estoque.getId());

        partialUpdatedEstoque.quantidade(UPDATED_QUANTIDADE).criadoEm(UPDATED_CRIADO_EM).modificadoEm(UPDATED_MODIFICADO_EM);

        restEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstoque))
            )
            .andExpect(status().isOk());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
        Estoque testEstoque = estoqueList.get(estoqueList.size() - 1);
        assertThat(testEstoque.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testEstoque.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testEstoque.getModificadoEm()).isEqualTo(UPDATED_MODIFICADO_EM);
    }

    @Test
    @Transactional
    void patchNonExistingEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(longCount.incrementAndGet());

        // Create the Estoque
        EstoqueDTO estoqueDTO = estoqueMapper.toDto(estoque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estoqueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estoqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(longCount.incrementAndGet());

        // Create the Estoque
        EstoqueDTO estoqueDTO = estoqueMapper.toDto(estoque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estoqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(longCount.incrementAndGet());

        // Create the Estoque
        EstoqueDTO estoqueDTO = estoqueMapper.toDto(estoque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estoqueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstoque() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        int databaseSizeBeforeDelete = estoqueRepository.findAll().size();

        // Delete the estoque
        restEstoqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, estoque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
