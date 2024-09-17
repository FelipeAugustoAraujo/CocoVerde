package br.com.lisetech.cocoverde.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.Configuracao;
import br.com.lisetech.cocoverde.repository.ConfiguracaoRepository;
import br.com.lisetech.cocoverde.service.dto.ConfiguracaoDTO;
import br.com.lisetech.cocoverde.service.mapper.ConfiguracaoMapper;
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
 * Integration tests for the {@link ConfiguracaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfiguracaoResourceIT {

    private static final String ENTITY_API_URL = "/api/configuracaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Autowired
    private ConfiguracaoMapper configuracaoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfiguracaoMockMvc;

    private Configuracao configuracao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Configuracao createEntity(EntityManager em) {
        Configuracao configuracao = new Configuracao();
        return configuracao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Configuracao createUpdatedEntity(EntityManager em) {
        Configuracao configuracao = new Configuracao();
        return configuracao;
    }

    @BeforeEach
    public void initTest() {
        configuracao = createEntity(em);
    }

    @Test
    @Transactional
    void createConfiguracao() throws Exception {
        int databaseSizeBeforeCreate = configuracaoRepository.findAll().size();
        // Create the Configuracao
        ConfiguracaoDTO configuracaoDTO = configuracaoMapper.toDto(configuracao);
        restConfiguracaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configuracaoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeCreate + 1);
        Configuracao testConfiguracao = configuracaoList.get(configuracaoList.size() - 1);
    }

    @Test
    @Transactional
    void createConfiguracaoWithExistingId() throws Exception {
        // Create the Configuracao with an existing ID
        configuracao.setId(1L);
        ConfiguracaoDTO configuracaoDTO = configuracaoMapper.toDto(configuracao);

        int databaseSizeBeforeCreate = configuracaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfiguracaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configuracaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConfiguracaos() throws Exception {
        // Initialize the database
        configuracaoRepository.saveAndFlush(configuracao);

        // Get all the configuracaoList
        restConfiguracaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configuracao.getId().intValue())));
    }

    @Test
    @Transactional
    void getConfiguracao() throws Exception {
        // Initialize the database
        configuracaoRepository.saveAndFlush(configuracao);

        // Get the configuracao
        restConfiguracaoMockMvc
            .perform(get(ENTITY_API_URL_ID, configuracao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(configuracao.getId().intValue()));
    }

    @Test
    @Transactional
    void getConfiguracaosByIdFiltering() throws Exception {
        // Initialize the database
        configuracaoRepository.saveAndFlush(configuracao);

        Long id = configuracao.getId();

        defaultConfiguracaoShouldBeFound("id.equals=" + id);
        defaultConfiguracaoShouldNotBeFound("id.notEquals=" + id);

        defaultConfiguracaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConfiguracaoShouldNotBeFound("id.greaterThan=" + id);

        defaultConfiguracaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConfiguracaoShouldNotBeFound("id.lessThan=" + id);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConfiguracaoShouldBeFound(String filter) throws Exception {
        restConfiguracaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configuracao.getId().intValue())));

        // Check, that the count call also returns 1
        restConfiguracaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConfiguracaoShouldNotBeFound(String filter) throws Exception {
        restConfiguracaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConfiguracaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConfiguracao() throws Exception {
        // Get the configuracao
        restConfiguracaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConfiguracao() throws Exception {
        // Initialize the database
        configuracaoRepository.saveAndFlush(configuracao);

        int databaseSizeBeforeUpdate = configuracaoRepository.findAll().size();

        // Update the configuracao
        Configuracao updatedConfiguracao = configuracaoRepository.findById(configuracao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedConfiguracao are not directly saved in db
        em.detach(updatedConfiguracao);
        ConfiguracaoDTO configuracaoDTO = configuracaoMapper.toDto(updatedConfiguracao);

        restConfiguracaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configuracaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configuracaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeUpdate);
        Configuracao testConfiguracao = configuracaoList.get(configuracaoList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingConfiguracao() throws Exception {
        int databaseSizeBeforeUpdate = configuracaoRepository.findAll().size();
        configuracao.setId(longCount.incrementAndGet());

        // Create the Configuracao
        ConfiguracaoDTO configuracaoDTO = configuracaoMapper.toDto(configuracao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfiguracaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configuracaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configuracaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfiguracao() throws Exception {
        int databaseSizeBeforeUpdate = configuracaoRepository.findAll().size();
        configuracao.setId(longCount.incrementAndGet());

        // Create the Configuracao
        ConfiguracaoDTO configuracaoDTO = configuracaoMapper.toDto(configuracao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfiguracaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configuracaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfiguracao() throws Exception {
        int databaseSizeBeforeUpdate = configuracaoRepository.findAll().size();
        configuracao.setId(longCount.incrementAndGet());

        // Create the Configuracao
        ConfiguracaoDTO configuracaoDTO = configuracaoMapper.toDto(configuracao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfiguracaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configuracaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfiguracaoWithPatch() throws Exception {
        // Initialize the database
        configuracaoRepository.saveAndFlush(configuracao);

        int databaseSizeBeforeUpdate = configuracaoRepository.findAll().size();

        // Update the configuracao using partial update
        Configuracao partialUpdatedConfiguracao = new Configuracao();
        partialUpdatedConfiguracao.setId(configuracao.getId());

        restConfiguracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfiguracao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfiguracao))
            )
            .andExpect(status().isOk());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeUpdate);
        Configuracao testConfiguracao = configuracaoList.get(configuracaoList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateConfiguracaoWithPatch() throws Exception {
        // Initialize the database
        configuracaoRepository.saveAndFlush(configuracao);

        int databaseSizeBeforeUpdate = configuracaoRepository.findAll().size();

        // Update the configuracao using partial update
        Configuracao partialUpdatedConfiguracao = new Configuracao();
        partialUpdatedConfiguracao.setId(configuracao.getId());

        restConfiguracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfiguracao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfiguracao))
            )
            .andExpect(status().isOk());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeUpdate);
        Configuracao testConfiguracao = configuracaoList.get(configuracaoList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingConfiguracao() throws Exception {
        int databaseSizeBeforeUpdate = configuracaoRepository.findAll().size();
        configuracao.setId(longCount.incrementAndGet());

        // Create the Configuracao
        ConfiguracaoDTO configuracaoDTO = configuracaoMapper.toDto(configuracao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfiguracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, configuracaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configuracaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfiguracao() throws Exception {
        int databaseSizeBeforeUpdate = configuracaoRepository.findAll().size();
        configuracao.setId(longCount.incrementAndGet());

        // Create the Configuracao
        ConfiguracaoDTO configuracaoDTO = configuracaoMapper.toDto(configuracao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfiguracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configuracaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfiguracao() throws Exception {
        int databaseSizeBeforeUpdate = configuracaoRepository.findAll().size();
        configuracao.setId(longCount.incrementAndGet());

        // Create the Configuracao
        ConfiguracaoDTO configuracaoDTO = configuracaoMapper.toDto(configuracao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfiguracaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configuracaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Configuracao in the database
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfiguracao() throws Exception {
        // Initialize the database
        configuracaoRepository.saveAndFlush(configuracao);

        int databaseSizeBeforeDelete = configuracaoRepository.findAll().size();

        // Delete the configuracao
        restConfiguracaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, configuracao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Configuracao> configuracaoList = configuracaoRepository.findAll();
        assertThat(configuracaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
