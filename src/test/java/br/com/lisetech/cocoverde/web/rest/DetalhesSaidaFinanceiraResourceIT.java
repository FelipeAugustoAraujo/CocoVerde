package br.com.lisetech.cocoverde.web.rest;

import static br.com.lisetech.cocoverde.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.DetalhesSaidaFinanceira;
import br.com.lisetech.cocoverde.repository.DetalhesSaidaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.dto.DetalhesSaidaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.DetalhesSaidaFinanceiraMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link DetalhesSaidaFinanceiraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetalhesSaidaFinanceiraResourceIT {

    private static final Integer DEFAULT_QUANTIDADE_ITEM = 1;
    private static final Integer UPDATED_QUANTIDADE_ITEM = 2;
    private static final Integer SMALLER_QUANTIDADE_ITEM = 1 - 1;

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/detalhes-saida-financeiras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetalhesSaidaFinanceiraRepository detalhesSaidaFinanceiraRepository;

    @Autowired
    private DetalhesSaidaFinanceiraMapper detalhesSaidaFinanceiraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetalhesSaidaFinanceiraMockMvc;

    private DetalhesSaidaFinanceira detalhesSaidaFinanceira;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalhesSaidaFinanceira createEntity(EntityManager em) {
        DetalhesSaidaFinanceira detalhesSaidaFinanceira = new DetalhesSaidaFinanceira()
            .quantidadeItem(DEFAULT_QUANTIDADE_ITEM)
            .valor(DEFAULT_VALOR);
        return detalhesSaidaFinanceira;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalhesSaidaFinanceira createUpdatedEntity(EntityManager em) {
        DetalhesSaidaFinanceira detalhesSaidaFinanceira = new DetalhesSaidaFinanceira()
            .quantidadeItem(UPDATED_QUANTIDADE_ITEM)
            .valor(UPDATED_VALOR);
        return detalhesSaidaFinanceira;
    }

    @BeforeEach
    public void initTest() {
        detalhesSaidaFinanceira = createEntity(em);
    }

    @Test
    @Transactional
    void createDetalhesSaidaFinanceira() throws Exception {
        int databaseSizeBeforeCreate = detalhesSaidaFinanceiraRepository.findAll().size();
        // Create the DetalhesSaidaFinanceira
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO = detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceira);
        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesSaidaFinanceiraDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeCreate + 1);
        DetalhesSaidaFinanceira testDetalhesSaidaFinanceira = detalhesSaidaFinanceiraList.get(detalhesSaidaFinanceiraList.size() - 1);
        assertThat(testDetalhesSaidaFinanceira.getQuantidadeItem()).isEqualTo(DEFAULT_QUANTIDADE_ITEM);
        assertThat(testDetalhesSaidaFinanceira.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createDetalhesSaidaFinanceiraWithExistingId() throws Exception {
        // Create the DetalhesSaidaFinanceira with an existing ID
        detalhesSaidaFinanceira.setId(1L);
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO = detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceira);

        int databaseSizeBeforeCreate = detalhesSaidaFinanceiraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesSaidaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceiras() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList
        restDetalhesSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalhesSaidaFinanceira.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeItem").value(hasItem(DEFAULT_QUANTIDADE_ITEM)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))));
    }

    @Test
    @Transactional
    void getDetalhesSaidaFinanceira() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get the detalhesSaidaFinanceira
        restDetalhesSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL_ID, detalhesSaidaFinanceira.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detalhesSaidaFinanceira.getId().intValue()))
            .andExpect(jsonPath("$.quantidadeItem").value(DEFAULT_QUANTIDADE_ITEM))
            .andExpect(jsonPath("$.valor").value(sameNumber(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    void getDetalhesSaidaFinanceirasByIdFiltering() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        Long id = detalhesSaidaFinanceira.getId();

        defaultDetalhesSaidaFinanceiraShouldBeFound("id.equals=" + id);
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("id.notEquals=" + id);

        defaultDetalhesSaidaFinanceiraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("id.greaterThan=" + id);

        defaultDetalhesSaidaFinanceiraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByQuantidadeItemIsEqualToSomething() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem equals to DEFAULT_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldBeFound("quantidadeItem.equals=" + DEFAULT_QUANTIDADE_ITEM);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem equals to UPDATED_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("quantidadeItem.equals=" + UPDATED_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByQuantidadeItemIsInShouldWork() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem in DEFAULT_QUANTIDADE_ITEM or UPDATED_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldBeFound("quantidadeItem.in=" + DEFAULT_QUANTIDADE_ITEM + "," + UPDATED_QUANTIDADE_ITEM);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem equals to UPDATED_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("quantidadeItem.in=" + UPDATED_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByQuantidadeItemIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem is not null
        defaultDetalhesSaidaFinanceiraShouldBeFound("quantidadeItem.specified=true");

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem is null
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("quantidadeItem.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByQuantidadeItemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem is greater than or equal to DEFAULT_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldBeFound("quantidadeItem.greaterThanOrEqual=" + DEFAULT_QUANTIDADE_ITEM);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem is greater than or equal to UPDATED_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("quantidadeItem.greaterThanOrEqual=" + UPDATED_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByQuantidadeItemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem is less than or equal to DEFAULT_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldBeFound("quantidadeItem.lessThanOrEqual=" + DEFAULT_QUANTIDADE_ITEM);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem is less than or equal to SMALLER_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("quantidadeItem.lessThanOrEqual=" + SMALLER_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByQuantidadeItemIsLessThanSomething() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem is less than DEFAULT_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("quantidadeItem.lessThan=" + DEFAULT_QUANTIDADE_ITEM);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem is less than UPDATED_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldBeFound("quantidadeItem.lessThan=" + UPDATED_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByQuantidadeItemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem is greater than DEFAULT_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("quantidadeItem.greaterThan=" + DEFAULT_QUANTIDADE_ITEM);

        // Get all the detalhesSaidaFinanceiraList where quantidadeItem is greater than SMALLER_QUANTIDADE_ITEM
        defaultDetalhesSaidaFinanceiraShouldBeFound("quantidadeItem.greaterThan=" + SMALLER_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where valor equals to DEFAULT_VALOR
        defaultDetalhesSaidaFinanceiraShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the detalhesSaidaFinanceiraList where valor equals to UPDATED_VALOR
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultDetalhesSaidaFinanceiraShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the detalhesSaidaFinanceiraList where valor equals to UPDATED_VALOR
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where valor is not null
        defaultDetalhesSaidaFinanceiraShouldBeFound("valor.specified=true");

        // Get all the detalhesSaidaFinanceiraList where valor is null
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where valor is greater than or equal to DEFAULT_VALOR
        defaultDetalhesSaidaFinanceiraShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the detalhesSaidaFinanceiraList where valor is greater than or equal to UPDATED_VALOR
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where valor is less than or equal to DEFAULT_VALOR
        defaultDetalhesSaidaFinanceiraShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the detalhesSaidaFinanceiraList where valor is less than or equal to SMALLER_VALOR
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where valor is less than DEFAULT_VALOR
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the detalhesSaidaFinanceiraList where valor is less than UPDATED_VALOR
        defaultDetalhesSaidaFinanceiraShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesSaidaFinanceirasByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        // Get all the detalhesSaidaFinanceiraList where valor is greater than DEFAULT_VALOR
        defaultDetalhesSaidaFinanceiraShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the detalhesSaidaFinanceiraList where valor is greater than SMALLER_VALOR
        defaultDetalhesSaidaFinanceiraShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDetalhesSaidaFinanceiraShouldBeFound(String filter) throws Exception {
        restDetalhesSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalhesSaidaFinanceira.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeItem").value(hasItem(DEFAULT_QUANTIDADE_ITEM)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))));

        // Check, that the count call also returns 1
        restDetalhesSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDetalhesSaidaFinanceiraShouldNotBeFound(String filter) throws Exception {
        restDetalhesSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDetalhesSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDetalhesSaidaFinanceira() throws Exception {
        // Get the detalhesSaidaFinanceira
        restDetalhesSaidaFinanceiraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetalhesSaidaFinanceira() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        int databaseSizeBeforeUpdate = detalhesSaidaFinanceiraRepository.findAll().size();

        // Update the detalhesSaidaFinanceira
        DetalhesSaidaFinanceira updatedDetalhesSaidaFinanceira = detalhesSaidaFinanceiraRepository
            .findById(detalhesSaidaFinanceira.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDetalhesSaidaFinanceira are not directly saved in db
        em.detach(updatedDetalhesSaidaFinanceira);
        updatedDetalhesSaidaFinanceira.quantidadeItem(UPDATED_QUANTIDADE_ITEM).valor(UPDATED_VALOR);
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO = detalhesSaidaFinanceiraMapper.toDto(updatedDetalhesSaidaFinanceira);

        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalhesSaidaFinanceiraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesSaidaFinanceiraDTO))
            )
            .andExpect(status().isOk());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        DetalhesSaidaFinanceira testDetalhesSaidaFinanceira = detalhesSaidaFinanceiraList.get(detalhesSaidaFinanceiraList.size() - 1);
        assertThat(testDetalhesSaidaFinanceira.getQuantidadeItem()).isEqualTo(UPDATED_QUANTIDADE_ITEM);
        assertThat(testDetalhesSaidaFinanceira.getValor()).isEqualByComparingTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingDetalhesSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesSaidaFinanceiraRepository.findAll().size();
        detalhesSaidaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesSaidaFinanceira
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO = detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceira);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalhesSaidaFinanceiraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesSaidaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetalhesSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesSaidaFinanceiraRepository.findAll().size();
        detalhesSaidaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesSaidaFinanceira
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO = detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesSaidaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetalhesSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesSaidaFinanceiraRepository.findAll().size();
        detalhesSaidaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesSaidaFinanceira
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO = detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesSaidaFinanceiraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetalhesSaidaFinanceiraWithPatch() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        int databaseSizeBeforeUpdate = detalhesSaidaFinanceiraRepository.findAll().size();

        // Update the detalhesSaidaFinanceira using partial update
        DetalhesSaidaFinanceira partialUpdatedDetalhesSaidaFinanceira = new DetalhesSaidaFinanceira();
        partialUpdatedDetalhesSaidaFinanceira.setId(detalhesSaidaFinanceira.getId());

        partialUpdatedDetalhesSaidaFinanceira.quantidadeItem(UPDATED_QUANTIDADE_ITEM);

        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalhesSaidaFinanceira.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalhesSaidaFinanceira))
            )
            .andExpect(status().isOk());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        DetalhesSaidaFinanceira testDetalhesSaidaFinanceira = detalhesSaidaFinanceiraList.get(detalhesSaidaFinanceiraList.size() - 1);
        assertThat(testDetalhesSaidaFinanceira.getQuantidadeItem()).isEqualTo(UPDATED_QUANTIDADE_ITEM);
        assertThat(testDetalhesSaidaFinanceira.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateDetalhesSaidaFinanceiraWithPatch() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        int databaseSizeBeforeUpdate = detalhesSaidaFinanceiraRepository.findAll().size();

        // Update the detalhesSaidaFinanceira using partial update
        DetalhesSaidaFinanceira partialUpdatedDetalhesSaidaFinanceira = new DetalhesSaidaFinanceira();
        partialUpdatedDetalhesSaidaFinanceira.setId(detalhesSaidaFinanceira.getId());

        partialUpdatedDetalhesSaidaFinanceira.quantidadeItem(UPDATED_QUANTIDADE_ITEM).valor(UPDATED_VALOR);

        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalhesSaidaFinanceira.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalhesSaidaFinanceira))
            )
            .andExpect(status().isOk());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        DetalhesSaidaFinanceira testDetalhesSaidaFinanceira = detalhesSaidaFinanceiraList.get(detalhesSaidaFinanceiraList.size() - 1);
        assertThat(testDetalhesSaidaFinanceira.getQuantidadeItem()).isEqualTo(UPDATED_QUANTIDADE_ITEM);
        assertThat(testDetalhesSaidaFinanceira.getValor()).isEqualByComparingTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingDetalhesSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesSaidaFinanceiraRepository.findAll().size();
        detalhesSaidaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesSaidaFinanceira
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO = detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceira);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detalhesSaidaFinanceiraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalhesSaidaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetalhesSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesSaidaFinanceiraRepository.findAll().size();
        detalhesSaidaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesSaidaFinanceira
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO = detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalhesSaidaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetalhesSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesSaidaFinanceiraRepository.findAll().size();
        detalhesSaidaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesSaidaFinanceira
        DetalhesSaidaFinanceiraDTO detalhesSaidaFinanceiraDTO = detalhesSaidaFinanceiraMapper.toDto(detalhesSaidaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhesSaidaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalhesSaidaFinanceiraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalhesSaidaFinanceira in the database
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetalhesSaidaFinanceira() throws Exception {
        // Initialize the database
        detalhesSaidaFinanceiraRepository.saveAndFlush(detalhesSaidaFinanceira);

        int databaseSizeBeforeDelete = detalhesSaidaFinanceiraRepository.findAll().size();

        // Delete the detalhesSaidaFinanceira
        restDetalhesSaidaFinanceiraMockMvc
            .perform(delete(ENTITY_API_URL_ID, detalhesSaidaFinanceira.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalhesSaidaFinanceira> detalhesSaidaFinanceiraList = detalhesSaidaFinanceiraRepository.findAll();
        assertThat(detalhesSaidaFinanceiraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
