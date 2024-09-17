package br.com.lisetech.cocoverde.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.DiaTrabalho;
import br.com.lisetech.cocoverde.repository.DiaTrabalhoRepository;
import br.com.lisetech.cocoverde.service.dto.DiaTrabalhoDTO;
import br.com.lisetech.cocoverde.service.mapper.DiaTrabalhoMapper;
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
 * Integration tests for the {@link DiaTrabalhoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiaTrabalhoResourceIT {

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/dia-trabalhos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiaTrabalhoRepository diaTrabalhoRepository;

    @Autowired
    private DiaTrabalhoMapper diaTrabalhoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiaTrabalhoMockMvc;

    private DiaTrabalho diaTrabalho;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiaTrabalho createEntity(EntityManager em) {
        DiaTrabalho diaTrabalho = new DiaTrabalho().data(DEFAULT_DATA);
        return diaTrabalho;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiaTrabalho createUpdatedEntity(EntityManager em) {
        DiaTrabalho diaTrabalho = new DiaTrabalho().data(UPDATED_DATA);
        return diaTrabalho;
    }

    @BeforeEach
    public void initTest() {
        diaTrabalho = createEntity(em);
    }

    @Test
    @Transactional
    void createDiaTrabalho() throws Exception {
        int databaseSizeBeforeCreate = diaTrabalhoRepository.findAll().size();
        // Create the DiaTrabalho
        DiaTrabalhoDTO diaTrabalhoDTO = diaTrabalhoMapper.toDto(diaTrabalho);
        restDiaTrabalhoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diaTrabalhoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeCreate + 1);
        DiaTrabalho testDiaTrabalho = diaTrabalhoList.get(diaTrabalhoList.size() - 1);
        assertThat(testDiaTrabalho.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    void createDiaTrabalhoWithExistingId() throws Exception {
        // Create the DiaTrabalho with an existing ID
        diaTrabalho.setId(1L);
        DiaTrabalhoDTO diaTrabalhoDTO = diaTrabalhoMapper.toDto(diaTrabalho);

        int databaseSizeBeforeCreate = diaTrabalhoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiaTrabalhoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiaTrabalhos() throws Exception {
        // Initialize the database
        diaTrabalhoRepository.saveAndFlush(diaTrabalho);

        // Get all the diaTrabalhoList
        restDiaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diaTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    @Transactional
    void getDiaTrabalho() throws Exception {
        // Initialize the database
        diaTrabalhoRepository.saveAndFlush(diaTrabalho);

        // Get the diaTrabalho
        restDiaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL_ID, diaTrabalho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diaTrabalho.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    void getDiaTrabalhosByIdFiltering() throws Exception {
        // Initialize the database
        diaTrabalhoRepository.saveAndFlush(diaTrabalho);

        Long id = diaTrabalho.getId();

        defaultDiaTrabalhoShouldBeFound("id.equals=" + id);
        defaultDiaTrabalhoShouldNotBeFound("id.notEquals=" + id);

        defaultDiaTrabalhoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDiaTrabalhoShouldNotBeFound("id.greaterThan=" + id);

        defaultDiaTrabalhoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDiaTrabalhoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDiaTrabalhosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        diaTrabalhoRepository.saveAndFlush(diaTrabalho);

        // Get all the diaTrabalhoList where data equals to DEFAULT_DATA
        defaultDiaTrabalhoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the diaTrabalhoList where data equals to UPDATED_DATA
        defaultDiaTrabalhoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllDiaTrabalhosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        diaTrabalhoRepository.saveAndFlush(diaTrabalho);

        // Get all the diaTrabalhoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultDiaTrabalhoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the diaTrabalhoList where data equals to UPDATED_DATA
        defaultDiaTrabalhoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllDiaTrabalhosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        diaTrabalhoRepository.saveAndFlush(diaTrabalho);

        // Get all the diaTrabalhoList where data is not null
        defaultDiaTrabalhoShouldBeFound("data.specified=true");

        // Get all the diaTrabalhoList where data is null
        defaultDiaTrabalhoShouldNotBeFound("data.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDiaTrabalhoShouldBeFound(String filter) throws Exception {
        restDiaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diaTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));

        // Check, that the count call also returns 1
        restDiaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDiaTrabalhoShouldNotBeFound(String filter) throws Exception {
        restDiaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDiaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDiaTrabalho() throws Exception {
        // Get the diaTrabalho
        restDiaTrabalhoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDiaTrabalho() throws Exception {
        // Initialize the database
        diaTrabalhoRepository.saveAndFlush(diaTrabalho);

        int databaseSizeBeforeUpdate = diaTrabalhoRepository.findAll().size();

        // Update the diaTrabalho
        DiaTrabalho updatedDiaTrabalho = diaTrabalhoRepository.findById(diaTrabalho.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDiaTrabalho are not directly saved in db
        em.detach(updatedDiaTrabalho);
        updatedDiaTrabalho.data(UPDATED_DATA);
        DiaTrabalhoDTO diaTrabalhoDTO = diaTrabalhoMapper.toDto(updatedDiaTrabalho);

        restDiaTrabalhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diaTrabalhoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diaTrabalhoDTO))
            )
            .andExpect(status().isOk());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
        DiaTrabalho testDiaTrabalho = diaTrabalhoList.get(diaTrabalhoList.size() - 1);
        assertThat(testDiaTrabalho.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void putNonExistingDiaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = diaTrabalhoRepository.findAll().size();
        diaTrabalho.setId(longCount.incrementAndGet());

        // Create the DiaTrabalho
        DiaTrabalhoDTO diaTrabalhoDTO = diaTrabalhoMapper.toDto(diaTrabalho);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaTrabalhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diaTrabalhoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = diaTrabalhoRepository.findAll().size();
        diaTrabalho.setId(longCount.incrementAndGet());

        // Create the DiaTrabalho
        DiaTrabalhoDTO diaTrabalhoDTO = diaTrabalhoMapper.toDto(diaTrabalho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaTrabalhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = diaTrabalhoRepository.findAll().size();
        diaTrabalho.setId(longCount.incrementAndGet());

        // Create the DiaTrabalho
        DiaTrabalhoDTO diaTrabalhoDTO = diaTrabalhoMapper.toDto(diaTrabalho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaTrabalhoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diaTrabalhoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiaTrabalhoWithPatch() throws Exception {
        // Initialize the database
        diaTrabalhoRepository.saveAndFlush(diaTrabalho);

        int databaseSizeBeforeUpdate = diaTrabalhoRepository.findAll().size();

        // Update the diaTrabalho using partial update
        DiaTrabalho partialUpdatedDiaTrabalho = new DiaTrabalho();
        partialUpdatedDiaTrabalho.setId(diaTrabalho.getId());

        partialUpdatedDiaTrabalho.data(UPDATED_DATA);

        restDiaTrabalhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiaTrabalho.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiaTrabalho))
            )
            .andExpect(status().isOk());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
        DiaTrabalho testDiaTrabalho = diaTrabalhoList.get(diaTrabalhoList.size() - 1);
        assertThat(testDiaTrabalho.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void fullUpdateDiaTrabalhoWithPatch() throws Exception {
        // Initialize the database
        diaTrabalhoRepository.saveAndFlush(diaTrabalho);

        int databaseSizeBeforeUpdate = diaTrabalhoRepository.findAll().size();

        // Update the diaTrabalho using partial update
        DiaTrabalho partialUpdatedDiaTrabalho = new DiaTrabalho();
        partialUpdatedDiaTrabalho.setId(diaTrabalho.getId());

        partialUpdatedDiaTrabalho.data(UPDATED_DATA);

        restDiaTrabalhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiaTrabalho.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiaTrabalho))
            )
            .andExpect(status().isOk());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
        DiaTrabalho testDiaTrabalho = diaTrabalhoList.get(diaTrabalhoList.size() - 1);
        assertThat(testDiaTrabalho.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingDiaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = diaTrabalhoRepository.findAll().size();
        diaTrabalho.setId(longCount.incrementAndGet());

        // Create the DiaTrabalho
        DiaTrabalhoDTO diaTrabalhoDTO = diaTrabalhoMapper.toDto(diaTrabalho);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaTrabalhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diaTrabalhoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = diaTrabalhoRepository.findAll().size();
        diaTrabalho.setId(longCount.incrementAndGet());

        // Create the DiaTrabalho
        DiaTrabalhoDTO diaTrabalhoDTO = diaTrabalhoMapper.toDto(diaTrabalho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaTrabalhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = diaTrabalhoRepository.findAll().size();
        diaTrabalho.setId(longCount.incrementAndGet());

        // Create the DiaTrabalho
        DiaTrabalhoDTO diaTrabalhoDTO = diaTrabalhoMapper.toDto(diaTrabalho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaTrabalhoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(diaTrabalhoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiaTrabalho in the database
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiaTrabalho() throws Exception {
        // Initialize the database
        diaTrabalhoRepository.saveAndFlush(diaTrabalho);

        int databaseSizeBeforeDelete = diaTrabalhoRepository.findAll().size();

        // Delete the diaTrabalho
        restDiaTrabalhoMockMvc
            .perform(delete(ENTITY_API_URL_ID, diaTrabalho.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiaTrabalho> diaTrabalhoList = diaTrabalhoRepository.findAll();
        assertThat(diaTrabalhoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
