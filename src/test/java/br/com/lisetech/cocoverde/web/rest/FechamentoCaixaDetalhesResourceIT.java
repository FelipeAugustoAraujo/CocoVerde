package br.com.lisetech.cocoverde.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.domain.FechamentoCaixa;
import br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes;
import br.com.lisetech.cocoverde.domain.SaidaFinanceira;
import br.com.lisetech.cocoverde.repository.FechamentoCaixaDetalhesRepository;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDetalhesDTO;
import br.com.lisetech.cocoverde.service.mapper.FechamentoCaixaDetalhesMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link FechamentoCaixaDetalhesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FechamentoCaixaDetalhesResourceIT {

    private static final String ENTITY_API_URL = "/api/fechamento-caixa-detalhes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FechamentoCaixaDetalhesRepository fechamentoCaixaDetalhesRepository;

    @Autowired
    private FechamentoCaixaDetalhesMapper fechamentoCaixaDetalhesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFechamentoCaixaDetalhesMockMvc;

    private FechamentoCaixaDetalhes fechamentoCaixaDetalhes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FechamentoCaixaDetalhes createEntity(EntityManager em) {
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes = new FechamentoCaixaDetalhes();
        return fechamentoCaixaDetalhes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FechamentoCaixaDetalhes createUpdatedEntity(EntityManager em) {
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes = new FechamentoCaixaDetalhes();
        return fechamentoCaixaDetalhes;
    }

    @BeforeEach
    public void initTest() {
        fechamentoCaixaDetalhes = createEntity(em);
    }

    @Test
    @Transactional
    void createFechamentoCaixaDetalhes() throws Exception {
        int databaseSizeBeforeCreate = fechamentoCaixaDetalhesRepository.findAll().size();
        // Create the FechamentoCaixaDetalhes
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO = fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhes);
        restFechamentoCaixaDetalhesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDetalhesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeCreate + 1);
        FechamentoCaixaDetalhes testFechamentoCaixaDetalhes = fechamentoCaixaDetalhesList.get(fechamentoCaixaDetalhesList.size() - 1);
    }

    @Test
    @Transactional
    void createFechamentoCaixaDetalhesWithExistingId() throws Exception {
        // Create the FechamentoCaixaDetalhes with an existing ID
        fechamentoCaixaDetalhes.setId(1L);
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO = fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhes);

        int databaseSizeBeforeCreate = fechamentoCaixaDetalhesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFechamentoCaixaDetalhesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDetalhesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixaDetalhes() throws Exception {
        // Initialize the database
        fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);

        // Get all the fechamentoCaixaDetalhesList
        restFechamentoCaixaDetalhesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fechamentoCaixaDetalhes.getId().intValue())));
    }

    @Test
    @Transactional
    void getFechamentoCaixaDetalhes() throws Exception {
        // Initialize the database
        fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);

        // Get the fechamentoCaixaDetalhes
        restFechamentoCaixaDetalhesMockMvc
            .perform(get(ENTITY_API_URL_ID, fechamentoCaixaDetalhes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fechamentoCaixaDetalhes.getId().intValue()));
    }

    @Test
    @Transactional
    void getFechamentoCaixaDetalhesByIdFiltering() throws Exception {
        // Initialize the database
        fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);

        Long id = fechamentoCaixaDetalhes.getId();

        defaultFechamentoCaixaDetalhesShouldBeFound("id.equals=" + id);
        defaultFechamentoCaixaDetalhesShouldNotBeFound("id.notEquals=" + id);

        defaultFechamentoCaixaDetalhesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFechamentoCaixaDetalhesShouldNotBeFound("id.greaterThan=" + id);

        defaultFechamentoCaixaDetalhesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFechamentoCaixaDetalhesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixaDetalhesByFechamentoCaixaIsEqualToSomething() throws Exception {
        FechamentoCaixa fechamentoCaixa;
        if (TestUtil.findAll(em, FechamentoCaixa.class).isEmpty()) {
            fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);
            fechamentoCaixa = FechamentoCaixaResourceIT.createEntity(em);
        } else {
            fechamentoCaixa = TestUtil.findAll(em, FechamentoCaixa.class).get(0);
        }
        em.persist(fechamentoCaixa);
        em.flush();
        fechamentoCaixaDetalhes.setFechamentoCaixa(fechamentoCaixa);
        fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);
        Long fechamentoCaixaId = fechamentoCaixa.getId();
        // Get all the fechamentoCaixaDetalhesList where fechamentoCaixa equals to fechamentoCaixaId
        defaultFechamentoCaixaDetalhesShouldBeFound("fechamentoCaixaId.equals=" + fechamentoCaixaId);

        // Get all the fechamentoCaixaDetalhesList where fechamentoCaixa equals to (fechamentoCaixaId + 1)
        defaultFechamentoCaixaDetalhesShouldNotBeFound("fechamentoCaixaId.equals=" + (fechamentoCaixaId + 1));
    }

    @Test
    @Transactional
    void getAllFechamentoCaixaDetalhesByEntradaFinanceiraIsEqualToSomething() throws Exception {
        EntradaFinanceira entradaFinanceira;
        if (TestUtil.findAll(em, EntradaFinanceira.class).isEmpty()) {
            fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);
            entradaFinanceira = EntradaFinanceiraResourceIT.createEntity(em);
        } else {
            entradaFinanceira = TestUtil.findAll(em, EntradaFinanceira.class).get(0);
        }
        em.persist(entradaFinanceira);
        em.flush();
        fechamentoCaixaDetalhes.addEntradaFinanceira(entradaFinanceira);
        fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);
        Long entradaFinanceiraId = entradaFinanceira.getId();
        // Get all the fechamentoCaixaDetalhesList where entradaFinanceira equals to entradaFinanceiraId
        defaultFechamentoCaixaDetalhesShouldBeFound("entradaFinanceiraId.equals=" + entradaFinanceiraId);

        // Get all the fechamentoCaixaDetalhesList where entradaFinanceira equals to (entradaFinanceiraId + 1)
        defaultFechamentoCaixaDetalhesShouldNotBeFound("entradaFinanceiraId.equals=" + (entradaFinanceiraId + 1));
    }

    @Test
    @Transactional
    void getAllFechamentoCaixaDetalhesBySaidaFinanceiraIsEqualToSomething() throws Exception {
        SaidaFinanceira saidaFinanceira;
        if (TestUtil.findAll(em, SaidaFinanceira.class).isEmpty()) {
            fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);
            saidaFinanceira = SaidaFinanceiraResourceIT.createEntity(em);
        } else {
            saidaFinanceira = TestUtil.findAll(em, SaidaFinanceira.class).get(0);
        }
        em.persist(saidaFinanceira);
        em.flush();
        fechamentoCaixaDetalhes.addSaidaFinanceira(saidaFinanceira);
        fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);
        Long saidaFinanceiraId = saidaFinanceira.getId();
        // Get all the fechamentoCaixaDetalhesList where saidaFinanceira equals to saidaFinanceiraId
        defaultFechamentoCaixaDetalhesShouldBeFound("saidaFinanceiraId.equals=" + saidaFinanceiraId);

        // Get all the fechamentoCaixaDetalhesList where saidaFinanceira equals to (saidaFinanceiraId + 1)
        defaultFechamentoCaixaDetalhesShouldNotBeFound("saidaFinanceiraId.equals=" + (saidaFinanceiraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFechamentoCaixaDetalhesShouldBeFound(String filter) throws Exception {
        restFechamentoCaixaDetalhesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fechamentoCaixaDetalhes.getId().intValue())));

        // Check, that the count call also returns 1
        restFechamentoCaixaDetalhesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFechamentoCaixaDetalhesShouldNotBeFound(String filter) throws Exception {
        restFechamentoCaixaDetalhesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFechamentoCaixaDetalhesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFechamentoCaixaDetalhes() throws Exception {
        // Get the fechamentoCaixaDetalhes
        restFechamentoCaixaDetalhesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFechamentoCaixaDetalhes() throws Exception {
        // Initialize the database
        fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);

        int databaseSizeBeforeUpdate = fechamentoCaixaDetalhesRepository.findAll().size();

        // Update the fechamentoCaixaDetalhes
        FechamentoCaixaDetalhes updatedFechamentoCaixaDetalhes = fechamentoCaixaDetalhesRepository
            .findById(fechamentoCaixaDetalhes.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFechamentoCaixaDetalhes are not directly saved in db
        em.detach(updatedFechamentoCaixaDetalhes);
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO = fechamentoCaixaDetalhesMapper.toDto(updatedFechamentoCaixaDetalhes);

        restFechamentoCaixaDetalhesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fechamentoCaixaDetalhesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDetalhesDTO))
            )
            .andExpect(status().isOk());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeUpdate);
        FechamentoCaixaDetalhes testFechamentoCaixaDetalhes = fechamentoCaixaDetalhesList.get(fechamentoCaixaDetalhesList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingFechamentoCaixaDetalhes() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaDetalhesRepository.findAll().size();
        fechamentoCaixaDetalhes.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixaDetalhes
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO = fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFechamentoCaixaDetalhesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fechamentoCaixaDetalhesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDetalhesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFechamentoCaixaDetalhes() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaDetalhesRepository.findAll().size();
        fechamentoCaixaDetalhes.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixaDetalhes
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO = fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFechamentoCaixaDetalhesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDetalhesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFechamentoCaixaDetalhes() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaDetalhesRepository.findAll().size();
        fechamentoCaixaDetalhes.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixaDetalhes
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO = fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFechamentoCaixaDetalhesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDetalhesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFechamentoCaixaDetalhesWithPatch() throws Exception {
        // Initialize the database
        fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);

        int databaseSizeBeforeUpdate = fechamentoCaixaDetalhesRepository.findAll().size();

        // Update the fechamentoCaixaDetalhes using partial update
        FechamentoCaixaDetalhes partialUpdatedFechamentoCaixaDetalhes = new FechamentoCaixaDetalhes();
        partialUpdatedFechamentoCaixaDetalhes.setId(fechamentoCaixaDetalhes.getId());

        restFechamentoCaixaDetalhesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFechamentoCaixaDetalhes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFechamentoCaixaDetalhes))
            )
            .andExpect(status().isOk());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeUpdate);
        FechamentoCaixaDetalhes testFechamentoCaixaDetalhes = fechamentoCaixaDetalhesList.get(fechamentoCaixaDetalhesList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateFechamentoCaixaDetalhesWithPatch() throws Exception {
        // Initialize the database
        fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);

        int databaseSizeBeforeUpdate = fechamentoCaixaDetalhesRepository.findAll().size();

        // Update the fechamentoCaixaDetalhes using partial update
        FechamentoCaixaDetalhes partialUpdatedFechamentoCaixaDetalhes = new FechamentoCaixaDetalhes();
        partialUpdatedFechamentoCaixaDetalhes.setId(fechamentoCaixaDetalhes.getId());

        restFechamentoCaixaDetalhesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFechamentoCaixaDetalhes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFechamentoCaixaDetalhes))
            )
            .andExpect(status().isOk());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeUpdate);
        FechamentoCaixaDetalhes testFechamentoCaixaDetalhes = fechamentoCaixaDetalhesList.get(fechamentoCaixaDetalhesList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingFechamentoCaixaDetalhes() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaDetalhesRepository.findAll().size();
        fechamentoCaixaDetalhes.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixaDetalhes
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO = fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFechamentoCaixaDetalhesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fechamentoCaixaDetalhesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDetalhesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFechamentoCaixaDetalhes() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaDetalhesRepository.findAll().size();
        fechamentoCaixaDetalhes.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixaDetalhes
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO = fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFechamentoCaixaDetalhesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDetalhesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFechamentoCaixaDetalhes() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaDetalhesRepository.findAll().size();
        fechamentoCaixaDetalhes.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixaDetalhes
        FechamentoCaixaDetalhesDTO fechamentoCaixaDetalhesDTO = fechamentoCaixaDetalhesMapper.toDto(fechamentoCaixaDetalhes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFechamentoCaixaDetalhesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDetalhesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FechamentoCaixaDetalhes in the database
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFechamentoCaixaDetalhes() throws Exception {
        // Initialize the database
        fechamentoCaixaDetalhesRepository.saveAndFlush(fechamentoCaixaDetalhes);

        int databaseSizeBeforeDelete = fechamentoCaixaDetalhesRepository.findAll().size();

        // Delete the fechamentoCaixaDetalhes
        restFechamentoCaixaDetalhesMockMvc
            .perform(delete(ENTITY_API_URL_ID, fechamentoCaixaDetalhes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FechamentoCaixaDetalhes> fechamentoCaixaDetalhesList = fechamentoCaixaDetalhesRepository.findAll();
        assertThat(fechamentoCaixaDetalhesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
