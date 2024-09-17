package br.com.lisetech.cocoverde.web.rest;

import static br.com.lisetech.cocoverde.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceira;
import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.domain.Produto;
import br.com.lisetech.cocoverde.repository.DetalhesEntradaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.dto.DetalhesEntradaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.DetalhesEntradaFinanceiraMapper;
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
 * Integration tests for the {@link DetalhesEntradaFinanceiraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetalhesEntradaFinanceiraResourceIT {

    private static final Integer DEFAULT_QUANTIDADE_ITEM = 1;
    private static final Integer UPDATED_QUANTIDADE_ITEM = 2;
    private static final Integer SMALLER_QUANTIDADE_ITEM = 1 - 1;

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/detalhes-entrada-financeiras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetalhesEntradaFinanceiraRepository detalhesEntradaFinanceiraRepository;

    @Autowired
    private DetalhesEntradaFinanceiraMapper detalhesEntradaFinanceiraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetalhesEntradaFinanceiraMockMvc;

    private DetalhesEntradaFinanceira detalhesEntradaFinanceira;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalhesEntradaFinanceira createEntity(EntityManager em) {
        DetalhesEntradaFinanceira detalhesEntradaFinanceira = new DetalhesEntradaFinanceira()
            .quantidadeItem(DEFAULT_QUANTIDADE_ITEM)
            .valor(DEFAULT_VALOR);
        return detalhesEntradaFinanceira;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalhesEntradaFinanceira createUpdatedEntity(EntityManager em) {
        DetalhesEntradaFinanceira detalhesEntradaFinanceira = new DetalhesEntradaFinanceira()
            .quantidadeItem(UPDATED_QUANTIDADE_ITEM)
            .valor(UPDATED_VALOR);
        return detalhesEntradaFinanceira;
    }

    @BeforeEach
    public void initTest() {
        detalhesEntradaFinanceira = createEntity(em);
    }

    @Test
    @Transactional
    void createDetalhesEntradaFinanceira() throws Exception {
        int databaseSizeBeforeCreate = detalhesEntradaFinanceiraRepository.findAll().size();
        // Create the DetalhesEntradaFinanceira
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO = detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceira);
        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesEntradaFinanceiraDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeCreate + 1);
        DetalhesEntradaFinanceira testDetalhesEntradaFinanceira = detalhesEntradaFinanceiraList.get(
            detalhesEntradaFinanceiraList.size() - 1
        );
        assertThat(testDetalhesEntradaFinanceira.getQuantidadeItem()).isEqualTo(DEFAULT_QUANTIDADE_ITEM);
        assertThat(testDetalhesEntradaFinanceira.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createDetalhesEntradaFinanceiraWithExistingId() throws Exception {
        // Create the DetalhesEntradaFinanceira with an existing ID
        detalhesEntradaFinanceira.setId(1L);
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO = detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceira);

        int databaseSizeBeforeCreate = detalhesEntradaFinanceiraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesEntradaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceiras() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList
        restDetalhesEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalhesEntradaFinanceira.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeItem").value(hasItem(DEFAULT_QUANTIDADE_ITEM)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))));
    }

    @Test
    @Transactional
    void getDetalhesEntradaFinanceira() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get the detalhesEntradaFinanceira
        restDetalhesEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL_ID, detalhesEntradaFinanceira.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detalhesEntradaFinanceira.getId().intValue()))
            .andExpect(jsonPath("$.quantidadeItem").value(DEFAULT_QUANTIDADE_ITEM))
            .andExpect(jsonPath("$.valor").value(sameNumber(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    void getDetalhesEntradaFinanceirasByIdFiltering() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        Long id = detalhesEntradaFinanceira.getId();

        defaultDetalhesEntradaFinanceiraShouldBeFound("id.equals=" + id);
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("id.notEquals=" + id);

        defaultDetalhesEntradaFinanceiraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("id.greaterThan=" + id);

        defaultDetalhesEntradaFinanceiraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByQuantidadeItemIsEqualToSomething() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem equals to DEFAULT_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldBeFound("quantidadeItem.equals=" + DEFAULT_QUANTIDADE_ITEM);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem equals to UPDATED_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("quantidadeItem.equals=" + UPDATED_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByQuantidadeItemIsInShouldWork() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem in DEFAULT_QUANTIDADE_ITEM or UPDATED_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldBeFound("quantidadeItem.in=" + DEFAULT_QUANTIDADE_ITEM + "," + UPDATED_QUANTIDADE_ITEM);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem equals to UPDATED_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("quantidadeItem.in=" + UPDATED_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByQuantidadeItemIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem is not null
        defaultDetalhesEntradaFinanceiraShouldBeFound("quantidadeItem.specified=true");

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem is null
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("quantidadeItem.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByQuantidadeItemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem is greater than or equal to DEFAULT_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldBeFound("quantidadeItem.greaterThanOrEqual=" + DEFAULT_QUANTIDADE_ITEM);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem is greater than or equal to UPDATED_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("quantidadeItem.greaterThanOrEqual=" + UPDATED_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByQuantidadeItemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem is less than or equal to DEFAULT_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldBeFound("quantidadeItem.lessThanOrEqual=" + DEFAULT_QUANTIDADE_ITEM);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem is less than or equal to SMALLER_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("quantidadeItem.lessThanOrEqual=" + SMALLER_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByQuantidadeItemIsLessThanSomething() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem is less than DEFAULT_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("quantidadeItem.lessThan=" + DEFAULT_QUANTIDADE_ITEM);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem is less than UPDATED_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldBeFound("quantidadeItem.lessThan=" + UPDATED_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByQuantidadeItemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem is greater than DEFAULT_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("quantidadeItem.greaterThan=" + DEFAULT_QUANTIDADE_ITEM);

        // Get all the detalhesEntradaFinanceiraList where quantidadeItem is greater than SMALLER_QUANTIDADE_ITEM
        defaultDetalhesEntradaFinanceiraShouldBeFound("quantidadeItem.greaterThan=" + SMALLER_QUANTIDADE_ITEM);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where valor equals to DEFAULT_VALOR
        defaultDetalhesEntradaFinanceiraShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the detalhesEntradaFinanceiraList where valor equals to UPDATED_VALOR
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultDetalhesEntradaFinanceiraShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the detalhesEntradaFinanceiraList where valor equals to UPDATED_VALOR
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where valor is not null
        defaultDetalhesEntradaFinanceiraShouldBeFound("valor.specified=true");

        // Get all the detalhesEntradaFinanceiraList where valor is null
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where valor is greater than or equal to DEFAULT_VALOR
        defaultDetalhesEntradaFinanceiraShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the detalhesEntradaFinanceiraList where valor is greater than or equal to UPDATED_VALOR
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where valor is less than or equal to DEFAULT_VALOR
        defaultDetalhesEntradaFinanceiraShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the detalhesEntradaFinanceiraList where valor is less than or equal to SMALLER_VALOR
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where valor is less than DEFAULT_VALOR
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the detalhesEntradaFinanceiraList where valor is less than UPDATED_VALOR
        defaultDetalhesEntradaFinanceiraShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        // Get all the detalhesEntradaFinanceiraList where valor is greater than DEFAULT_VALOR
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the detalhesEntradaFinanceiraList where valor is greater than SMALLER_VALOR
        defaultDetalhesEntradaFinanceiraShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByProdutoIsEqualToSomething() throws Exception {
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);
            produto = ProdutoResourceIT.createEntity(em);
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        em.persist(produto);
        em.flush();
        detalhesEntradaFinanceira.setProduto(produto);
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);
        Long produtoId = produto.getId();
        // Get all the detalhesEntradaFinanceiraList where produto equals to produtoId
        defaultDetalhesEntradaFinanceiraShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the detalhesEntradaFinanceiraList where produto equals to (produtoId + 1)
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }

    @Test
    @Transactional
    void getAllDetalhesEntradaFinanceirasByEntradaFinanceiraIsEqualToSomething() throws Exception {
        EntradaFinanceira entradaFinanceira;
        if (TestUtil.findAll(em, EntradaFinanceira.class).isEmpty()) {
            detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);
            entradaFinanceira = EntradaFinanceiraResourceIT.createEntity(em);
        } else {
            entradaFinanceira = TestUtil.findAll(em, EntradaFinanceira.class).get(0);
        }
        em.persist(entradaFinanceira);
        em.flush();
        detalhesEntradaFinanceira.setEntradaFinanceira(entradaFinanceira);
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);
        Long entradaFinanceiraId = entradaFinanceira.getId();
        // Get all the detalhesEntradaFinanceiraList where entradaFinanceira equals to entradaFinanceiraId
        defaultDetalhesEntradaFinanceiraShouldBeFound("entradaFinanceiraId.equals=" + entradaFinanceiraId);

        // Get all the detalhesEntradaFinanceiraList where entradaFinanceira equals to (entradaFinanceiraId + 1)
        defaultDetalhesEntradaFinanceiraShouldNotBeFound("entradaFinanceiraId.equals=" + (entradaFinanceiraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDetalhesEntradaFinanceiraShouldBeFound(String filter) throws Exception {
        restDetalhesEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalhesEntradaFinanceira.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeItem").value(hasItem(DEFAULT_QUANTIDADE_ITEM)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))));

        // Check, that the count call also returns 1
        restDetalhesEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDetalhesEntradaFinanceiraShouldNotBeFound(String filter) throws Exception {
        restDetalhesEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDetalhesEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDetalhesEntradaFinanceira() throws Exception {
        // Get the detalhesEntradaFinanceira
        restDetalhesEntradaFinanceiraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetalhesEntradaFinanceira() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        int databaseSizeBeforeUpdate = detalhesEntradaFinanceiraRepository.findAll().size();

        // Update the detalhesEntradaFinanceira
        DetalhesEntradaFinanceira updatedDetalhesEntradaFinanceira = detalhesEntradaFinanceiraRepository
            .findById(detalhesEntradaFinanceira.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDetalhesEntradaFinanceira are not directly saved in db
        em.detach(updatedDetalhesEntradaFinanceira);
        updatedDetalhesEntradaFinanceira.quantidadeItem(UPDATED_QUANTIDADE_ITEM).valor(UPDATED_VALOR);
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO = detalhesEntradaFinanceiraMapper.toDto(updatedDetalhesEntradaFinanceira);

        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalhesEntradaFinanceiraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesEntradaFinanceiraDTO))
            )
            .andExpect(status().isOk());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        DetalhesEntradaFinanceira testDetalhesEntradaFinanceira = detalhesEntradaFinanceiraList.get(
            detalhesEntradaFinanceiraList.size() - 1
        );
        assertThat(testDetalhesEntradaFinanceira.getQuantidadeItem()).isEqualTo(UPDATED_QUANTIDADE_ITEM);
        assertThat(testDetalhesEntradaFinanceira.getValor()).isEqualByComparingTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingDetalhesEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesEntradaFinanceiraRepository.findAll().size();
        detalhesEntradaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesEntradaFinanceira
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO = detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceira);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalhesEntradaFinanceiraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesEntradaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetalhesEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesEntradaFinanceiraRepository.findAll().size();
        detalhesEntradaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesEntradaFinanceira
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO = detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesEntradaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetalhesEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesEntradaFinanceiraRepository.findAll().size();
        detalhesEntradaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesEntradaFinanceira
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO = detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhesEntradaFinanceiraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetalhesEntradaFinanceiraWithPatch() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        int databaseSizeBeforeUpdate = detalhesEntradaFinanceiraRepository.findAll().size();

        // Update the detalhesEntradaFinanceira using partial update
        DetalhesEntradaFinanceira partialUpdatedDetalhesEntradaFinanceira = new DetalhesEntradaFinanceira();
        partialUpdatedDetalhesEntradaFinanceira.setId(detalhesEntradaFinanceira.getId());

        partialUpdatedDetalhesEntradaFinanceira.quantidadeItem(UPDATED_QUANTIDADE_ITEM);

        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalhesEntradaFinanceira.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalhesEntradaFinanceira))
            )
            .andExpect(status().isOk());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        DetalhesEntradaFinanceira testDetalhesEntradaFinanceira = detalhesEntradaFinanceiraList.get(
            detalhesEntradaFinanceiraList.size() - 1
        );
        assertThat(testDetalhesEntradaFinanceira.getQuantidadeItem()).isEqualTo(UPDATED_QUANTIDADE_ITEM);
        assertThat(testDetalhesEntradaFinanceira.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateDetalhesEntradaFinanceiraWithPatch() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        int databaseSizeBeforeUpdate = detalhesEntradaFinanceiraRepository.findAll().size();

        // Update the detalhesEntradaFinanceira using partial update
        DetalhesEntradaFinanceira partialUpdatedDetalhesEntradaFinanceira = new DetalhesEntradaFinanceira();
        partialUpdatedDetalhesEntradaFinanceira.setId(detalhesEntradaFinanceira.getId());

        partialUpdatedDetalhesEntradaFinanceira.quantidadeItem(UPDATED_QUANTIDADE_ITEM).valor(UPDATED_VALOR);

        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalhesEntradaFinanceira.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalhesEntradaFinanceira))
            )
            .andExpect(status().isOk());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        DetalhesEntradaFinanceira testDetalhesEntradaFinanceira = detalhesEntradaFinanceiraList.get(
            detalhesEntradaFinanceiraList.size() - 1
        );
        assertThat(testDetalhesEntradaFinanceira.getQuantidadeItem()).isEqualTo(UPDATED_QUANTIDADE_ITEM);
        assertThat(testDetalhesEntradaFinanceira.getValor()).isEqualByComparingTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingDetalhesEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesEntradaFinanceiraRepository.findAll().size();
        detalhesEntradaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesEntradaFinanceira
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO = detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceira);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detalhesEntradaFinanceiraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalhesEntradaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetalhesEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesEntradaFinanceiraRepository.findAll().size();
        detalhesEntradaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesEntradaFinanceira
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO = detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalhesEntradaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetalhesEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = detalhesEntradaFinanceiraRepository.findAll().size();
        detalhesEntradaFinanceira.setId(longCount.incrementAndGet());

        // Create the DetalhesEntradaFinanceira
        DetalhesEntradaFinanceiraDTO detalhesEntradaFinanceiraDTO = detalhesEntradaFinanceiraMapper.toDto(detalhesEntradaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhesEntradaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalhesEntradaFinanceiraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalhesEntradaFinanceira in the database
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetalhesEntradaFinanceira() throws Exception {
        // Initialize the database
        detalhesEntradaFinanceiraRepository.saveAndFlush(detalhesEntradaFinanceira);

        int databaseSizeBeforeDelete = detalhesEntradaFinanceiraRepository.findAll().size();

        // Delete the detalhesEntradaFinanceira
        restDetalhesEntradaFinanceiraMockMvc
            .perform(delete(ENTITY_API_URL_ID, detalhesEntradaFinanceira.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalhesEntradaFinanceira> detalhesEntradaFinanceiraList = detalhesEntradaFinanceiraRepository.findAll();
        assertThat(detalhesEntradaFinanceiraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
