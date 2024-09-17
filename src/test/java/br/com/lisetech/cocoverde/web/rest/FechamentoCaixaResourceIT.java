package br.com.lisetech.cocoverde.web.rest;

import static br.com.lisetech.cocoverde.web.rest.TestUtil.sameInstant;
import static br.com.lisetech.cocoverde.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.FechamentoCaixa;
import br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes;
import br.com.lisetech.cocoverde.repository.FechamentoCaixaRepository;
import br.com.lisetech.cocoverde.service.dto.FechamentoCaixaDTO;
import br.com.lisetech.cocoverde.service.mapper.FechamentoCaixaMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link FechamentoCaixaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FechamentoCaixaResourceIT {

    private static final ZonedDateTime DEFAULT_DATA_INICIAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_INICIAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_INICIAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATA_FINAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_FINAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_FINAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_QUANTIDADE_COCOS_PERDIDOS = 1;
    private static final Integer UPDATED_QUANTIDADE_COCOS_PERDIDOS = 2;
    private static final Integer SMALLER_QUANTIDADE_COCOS_PERDIDOS = 1 - 1;

    private static final Integer DEFAULT_QUANTIDADE_COCOS_VENDIDOS = 1;
    private static final Integer UPDATED_QUANTIDADE_COCOS_VENDIDOS = 2;
    private static final Integer SMALLER_QUANTIDADE_COCOS_VENDIDOS = 1 - 1;

    private static final Integer DEFAULT_QUANTIDADE_COCO_SOBROU = 1;
    private static final Integer UPDATED_QUANTIDADE_COCO_SOBROU = 2;
    private static final Integer SMALLER_QUANTIDADE_COCO_SOBROU = 1 - 1;

    private static final Integer DEFAULT_DIVIDIDO_POR = 1;
    private static final Integer UPDATED_DIVIDIDO_POR = 2;
    private static final Integer SMALLER_DIVIDIDO_POR = 1 - 1;

    private static final BigDecimal DEFAULT_VALOR_TOTAL_COCO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_COCO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL_COCO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_TOTAL_COCO_PERDIDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_COCO_PERDIDO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL_COCO_PERDIDO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_POR_PESSOA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_POR_PESSOA = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_POR_PESSOA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_DESPESAS = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_DESPESAS = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_DESPESAS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_DINHEIRO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_DINHEIRO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_DINHEIRO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_CARTAO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_CARTAO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_CARTAO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/fechamento-caixas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FechamentoCaixaRepository fechamentoCaixaRepository;

    @Autowired
    private FechamentoCaixaMapper fechamentoCaixaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFechamentoCaixaMockMvc;

    private FechamentoCaixa fechamentoCaixa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FechamentoCaixa createEntity(EntityManager em) {
        FechamentoCaixa fechamentoCaixa = new FechamentoCaixa()
            .dataInicial(DEFAULT_DATA_INICIAL)
            .dataFinal(DEFAULT_DATA_FINAL)
            .quantidadeCocosPerdidos(DEFAULT_QUANTIDADE_COCOS_PERDIDOS)
            .quantidadeCocosVendidos(DEFAULT_QUANTIDADE_COCOS_VENDIDOS)
            .quantidadeCocoSobrou(DEFAULT_QUANTIDADE_COCO_SOBROU)
            .divididoPor(DEFAULT_DIVIDIDO_POR)
            .valorTotalCoco(DEFAULT_VALOR_TOTAL_COCO)
            .valorTotalCocoPerdido(DEFAULT_VALOR_TOTAL_COCO_PERDIDO)
            .valorPorPessoa(DEFAULT_VALOR_POR_PESSOA)
            .valorDespesas(DEFAULT_VALOR_DESPESAS)
            .valorDinheiro(DEFAULT_VALOR_DINHEIRO)
            .valorCartao(DEFAULT_VALOR_CARTAO)
            .valorTotal(DEFAULT_VALOR_TOTAL);
        return fechamentoCaixa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FechamentoCaixa createUpdatedEntity(EntityManager em) {
        FechamentoCaixa fechamentoCaixa = new FechamentoCaixa()
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .quantidadeCocosPerdidos(UPDATED_QUANTIDADE_COCOS_PERDIDOS)
            .quantidadeCocosVendidos(UPDATED_QUANTIDADE_COCOS_VENDIDOS)
            .quantidadeCocoSobrou(UPDATED_QUANTIDADE_COCO_SOBROU)
            .divididoPor(UPDATED_DIVIDIDO_POR)
            .valorTotalCoco(UPDATED_VALOR_TOTAL_COCO)
            .valorTotalCocoPerdido(UPDATED_VALOR_TOTAL_COCO_PERDIDO)
            .valorPorPessoa(UPDATED_VALOR_POR_PESSOA)
            .valorDespesas(UPDATED_VALOR_DESPESAS)
            .valorDinheiro(UPDATED_VALOR_DINHEIRO)
            .valorCartao(UPDATED_VALOR_CARTAO)
            .valorTotal(UPDATED_VALOR_TOTAL);
        return fechamentoCaixa;
    }

    @BeforeEach
    public void initTest() {
        fechamentoCaixa = createEntity(em);
    }

    @Test
    @Transactional
    void createFechamentoCaixa() throws Exception {
        int databaseSizeBeforeCreate = fechamentoCaixaRepository.findAll().size();
        // Create the FechamentoCaixa
        FechamentoCaixaDTO fechamentoCaixaDTO = fechamentoCaixaMapper.toDto(fechamentoCaixa);
        restFechamentoCaixaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeCreate + 1);
        FechamentoCaixa testFechamentoCaixa = fechamentoCaixaList.get(fechamentoCaixaList.size() - 1);
        assertThat(testFechamentoCaixa.getDataInicial()).isEqualTo(DEFAULT_DATA_INICIAL);
        assertThat(testFechamentoCaixa.getDataFinal()).isEqualTo(DEFAULT_DATA_FINAL);
        assertThat(testFechamentoCaixa.getQuantidadeCocosPerdidos()).isEqualTo(DEFAULT_QUANTIDADE_COCOS_PERDIDOS);
        assertThat(testFechamentoCaixa.getQuantidadeCocosVendidos()).isEqualTo(DEFAULT_QUANTIDADE_COCOS_VENDIDOS);
        assertThat(testFechamentoCaixa.getQuantidadeCocoSobrou()).isEqualTo(DEFAULT_QUANTIDADE_COCO_SOBROU);
        assertThat(testFechamentoCaixa.getDivididoPor()).isEqualTo(DEFAULT_DIVIDIDO_POR);
        assertThat(testFechamentoCaixa.getValorTotalCoco()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_COCO);
        assertThat(testFechamentoCaixa.getValorTotalCocoPerdido()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_COCO_PERDIDO);
        assertThat(testFechamentoCaixa.getValorPorPessoa()).isEqualByComparingTo(DEFAULT_VALOR_POR_PESSOA);
        assertThat(testFechamentoCaixa.getValorDespesas()).isEqualByComparingTo(DEFAULT_VALOR_DESPESAS);
        assertThat(testFechamentoCaixa.getValorDinheiro()).isEqualByComparingTo(DEFAULT_VALOR_DINHEIRO);
        assertThat(testFechamentoCaixa.getValorCartao()).isEqualByComparingTo(DEFAULT_VALOR_CARTAO);
        assertThat(testFechamentoCaixa.getValorTotal()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void createFechamentoCaixaWithExistingId() throws Exception {
        // Create the FechamentoCaixa with an existing ID
        fechamentoCaixa.setId(1L);
        FechamentoCaixaDTO fechamentoCaixaDTO = fechamentoCaixaMapper.toDto(fechamentoCaixa);

        int databaseSizeBeforeCreate = fechamentoCaixaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFechamentoCaixaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixas() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList
        restFechamentoCaixaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fechamentoCaixa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInicial").value(hasItem(sameInstant(DEFAULT_DATA_INICIAL))))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(sameInstant(DEFAULT_DATA_FINAL))))
            .andExpect(jsonPath("$.[*].quantidadeCocosPerdidos").value(hasItem(DEFAULT_QUANTIDADE_COCOS_PERDIDOS)))
            .andExpect(jsonPath("$.[*].quantidadeCocosVendidos").value(hasItem(DEFAULT_QUANTIDADE_COCOS_VENDIDOS)))
            .andExpect(jsonPath("$.[*].quantidadeCocoSobrou").value(hasItem(DEFAULT_QUANTIDADE_COCO_SOBROU)))
            .andExpect(jsonPath("$.[*].divididoPor").value(hasItem(DEFAULT_DIVIDIDO_POR)))
            .andExpect(jsonPath("$.[*].valorTotalCoco").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_COCO))))
            .andExpect(jsonPath("$.[*].valorTotalCocoPerdido").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_COCO_PERDIDO))))
            .andExpect(jsonPath("$.[*].valorPorPessoa").value(hasItem(sameNumber(DEFAULT_VALOR_POR_PESSOA))))
            .andExpect(jsonPath("$.[*].valorDespesas").value(hasItem(sameNumber(DEFAULT_VALOR_DESPESAS))))
            .andExpect(jsonPath("$.[*].valorDinheiro").value(hasItem(sameNumber(DEFAULT_VALOR_DINHEIRO))))
            .andExpect(jsonPath("$.[*].valorCartao").value(hasItem(sameNumber(DEFAULT_VALOR_CARTAO))))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL))));
    }

    @Test
    @Transactional
    void getFechamentoCaixa() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get the fechamentoCaixa
        restFechamentoCaixaMockMvc
            .perform(get(ENTITY_API_URL_ID, fechamentoCaixa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fechamentoCaixa.getId().intValue()))
            .andExpect(jsonPath("$.dataInicial").value(sameInstant(DEFAULT_DATA_INICIAL)))
            .andExpect(jsonPath("$.dataFinal").value(sameInstant(DEFAULT_DATA_FINAL)))
            .andExpect(jsonPath("$.quantidadeCocosPerdidos").value(DEFAULT_QUANTIDADE_COCOS_PERDIDOS))
            .andExpect(jsonPath("$.quantidadeCocosVendidos").value(DEFAULT_QUANTIDADE_COCOS_VENDIDOS))
            .andExpect(jsonPath("$.quantidadeCocoSobrou").value(DEFAULT_QUANTIDADE_COCO_SOBROU))
            .andExpect(jsonPath("$.divididoPor").value(DEFAULT_DIVIDIDO_POR))
            .andExpect(jsonPath("$.valorTotalCoco").value(sameNumber(DEFAULT_VALOR_TOTAL_COCO)))
            .andExpect(jsonPath("$.valorTotalCocoPerdido").value(sameNumber(DEFAULT_VALOR_TOTAL_COCO_PERDIDO)))
            .andExpect(jsonPath("$.valorPorPessoa").value(sameNumber(DEFAULT_VALOR_POR_PESSOA)))
            .andExpect(jsonPath("$.valorDespesas").value(sameNumber(DEFAULT_VALOR_DESPESAS)))
            .andExpect(jsonPath("$.valorDinheiro").value(sameNumber(DEFAULT_VALOR_DINHEIRO)))
            .andExpect(jsonPath("$.valorCartao").value(sameNumber(DEFAULT_VALOR_CARTAO)))
            .andExpect(jsonPath("$.valorTotal").value(sameNumber(DEFAULT_VALOR_TOTAL)));
    }

    @Test
    @Transactional
    void getFechamentoCaixasByIdFiltering() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        Long id = fechamentoCaixa.getId();

        defaultFechamentoCaixaShouldBeFound("id.equals=" + id);
        defaultFechamentoCaixaShouldNotBeFound("id.notEquals=" + id);

        defaultFechamentoCaixaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFechamentoCaixaShouldNotBeFound("id.greaterThan=" + id);

        defaultFechamentoCaixaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFechamentoCaixaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataInicialIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataInicial equals to DEFAULT_DATA_INICIAL
        defaultFechamentoCaixaShouldBeFound("dataInicial.equals=" + DEFAULT_DATA_INICIAL);

        // Get all the fechamentoCaixaList where dataInicial equals to UPDATED_DATA_INICIAL
        defaultFechamentoCaixaShouldNotBeFound("dataInicial.equals=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataInicialIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataInicial in DEFAULT_DATA_INICIAL or UPDATED_DATA_INICIAL
        defaultFechamentoCaixaShouldBeFound("dataInicial.in=" + DEFAULT_DATA_INICIAL + "," + UPDATED_DATA_INICIAL);

        // Get all the fechamentoCaixaList where dataInicial equals to UPDATED_DATA_INICIAL
        defaultFechamentoCaixaShouldNotBeFound("dataInicial.in=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataInicialIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataInicial is not null
        defaultFechamentoCaixaShouldBeFound("dataInicial.specified=true");

        // Get all the fechamentoCaixaList where dataInicial is null
        defaultFechamentoCaixaShouldNotBeFound("dataInicial.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataInicialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataInicial is greater than or equal to DEFAULT_DATA_INICIAL
        defaultFechamentoCaixaShouldBeFound("dataInicial.greaterThanOrEqual=" + DEFAULT_DATA_INICIAL);

        // Get all the fechamentoCaixaList where dataInicial is greater than or equal to UPDATED_DATA_INICIAL
        defaultFechamentoCaixaShouldNotBeFound("dataInicial.greaterThanOrEqual=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataInicialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataInicial is less than or equal to DEFAULT_DATA_INICIAL
        defaultFechamentoCaixaShouldBeFound("dataInicial.lessThanOrEqual=" + DEFAULT_DATA_INICIAL);

        // Get all the fechamentoCaixaList where dataInicial is less than or equal to SMALLER_DATA_INICIAL
        defaultFechamentoCaixaShouldNotBeFound("dataInicial.lessThanOrEqual=" + SMALLER_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataInicialIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataInicial is less than DEFAULT_DATA_INICIAL
        defaultFechamentoCaixaShouldNotBeFound("dataInicial.lessThan=" + DEFAULT_DATA_INICIAL);

        // Get all the fechamentoCaixaList where dataInicial is less than UPDATED_DATA_INICIAL
        defaultFechamentoCaixaShouldBeFound("dataInicial.lessThan=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataInicialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataInicial is greater than DEFAULT_DATA_INICIAL
        defaultFechamentoCaixaShouldNotBeFound("dataInicial.greaterThan=" + DEFAULT_DATA_INICIAL);

        // Get all the fechamentoCaixaList where dataInicial is greater than SMALLER_DATA_INICIAL
        defaultFechamentoCaixaShouldBeFound("dataInicial.greaterThan=" + SMALLER_DATA_INICIAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataFinal equals to DEFAULT_DATA_FINAL
        defaultFechamentoCaixaShouldBeFound("dataFinal.equals=" + DEFAULT_DATA_FINAL);

        // Get all the fechamentoCaixaList where dataFinal equals to UPDATED_DATA_FINAL
        defaultFechamentoCaixaShouldNotBeFound("dataFinal.equals=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataFinalIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataFinal in DEFAULT_DATA_FINAL or UPDATED_DATA_FINAL
        defaultFechamentoCaixaShouldBeFound("dataFinal.in=" + DEFAULT_DATA_FINAL + "," + UPDATED_DATA_FINAL);

        // Get all the fechamentoCaixaList where dataFinal equals to UPDATED_DATA_FINAL
        defaultFechamentoCaixaShouldNotBeFound("dataFinal.in=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataFinal is not null
        defaultFechamentoCaixaShouldBeFound("dataFinal.specified=true");

        // Get all the fechamentoCaixaList where dataFinal is null
        defaultFechamentoCaixaShouldNotBeFound("dataFinal.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataFinalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataFinal is greater than or equal to DEFAULT_DATA_FINAL
        defaultFechamentoCaixaShouldBeFound("dataFinal.greaterThanOrEqual=" + DEFAULT_DATA_FINAL);

        // Get all the fechamentoCaixaList where dataFinal is greater than or equal to UPDATED_DATA_FINAL
        defaultFechamentoCaixaShouldNotBeFound("dataFinal.greaterThanOrEqual=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataFinalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataFinal is less than or equal to DEFAULT_DATA_FINAL
        defaultFechamentoCaixaShouldBeFound("dataFinal.lessThanOrEqual=" + DEFAULT_DATA_FINAL);

        // Get all the fechamentoCaixaList where dataFinal is less than or equal to SMALLER_DATA_FINAL
        defaultFechamentoCaixaShouldNotBeFound("dataFinal.lessThanOrEqual=" + SMALLER_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataFinalIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataFinal is less than DEFAULT_DATA_FINAL
        defaultFechamentoCaixaShouldNotBeFound("dataFinal.lessThan=" + DEFAULT_DATA_FINAL);

        // Get all the fechamentoCaixaList where dataFinal is less than UPDATED_DATA_FINAL
        defaultFechamentoCaixaShouldBeFound("dataFinal.lessThan=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDataFinalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where dataFinal is greater than DEFAULT_DATA_FINAL
        defaultFechamentoCaixaShouldNotBeFound("dataFinal.greaterThan=" + DEFAULT_DATA_FINAL);

        // Get all the fechamentoCaixaList where dataFinal is greater than SMALLER_DATA_FINAL
        defaultFechamentoCaixaShouldBeFound("dataFinal.greaterThan=" + SMALLER_DATA_FINAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosPerdidosIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos equals to DEFAULT_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosPerdidos.equals=" + DEFAULT_QUANTIDADE_COCOS_PERDIDOS);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos equals to UPDATED_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosPerdidos.equals=" + UPDATED_QUANTIDADE_COCOS_PERDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosPerdidosIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos in DEFAULT_QUANTIDADE_COCOS_PERDIDOS or UPDATED_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldBeFound(
            "quantidadeCocosPerdidos.in=" + DEFAULT_QUANTIDADE_COCOS_PERDIDOS + "," + UPDATED_QUANTIDADE_COCOS_PERDIDOS
        );

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos equals to UPDATED_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosPerdidos.in=" + UPDATED_QUANTIDADE_COCOS_PERDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosPerdidosIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos is not null
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosPerdidos.specified=true");

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos is null
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosPerdidos.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosPerdidosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos is greater than or equal to DEFAULT_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosPerdidos.greaterThanOrEqual=" + DEFAULT_QUANTIDADE_COCOS_PERDIDOS);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos is greater than or equal to UPDATED_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosPerdidos.greaterThanOrEqual=" + UPDATED_QUANTIDADE_COCOS_PERDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosPerdidosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos is less than or equal to DEFAULT_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosPerdidos.lessThanOrEqual=" + DEFAULT_QUANTIDADE_COCOS_PERDIDOS);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos is less than or equal to SMALLER_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosPerdidos.lessThanOrEqual=" + SMALLER_QUANTIDADE_COCOS_PERDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosPerdidosIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos is less than DEFAULT_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosPerdidos.lessThan=" + DEFAULT_QUANTIDADE_COCOS_PERDIDOS);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos is less than UPDATED_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosPerdidos.lessThan=" + UPDATED_QUANTIDADE_COCOS_PERDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosPerdidosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos is greater than DEFAULT_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosPerdidos.greaterThan=" + DEFAULT_QUANTIDADE_COCOS_PERDIDOS);

        // Get all the fechamentoCaixaList where quantidadeCocosPerdidos is greater than SMALLER_QUANTIDADE_COCOS_PERDIDOS
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosPerdidos.greaterThan=" + SMALLER_QUANTIDADE_COCOS_PERDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosVendidosIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos equals to DEFAULT_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosVendidos.equals=" + DEFAULT_QUANTIDADE_COCOS_VENDIDOS);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos equals to UPDATED_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosVendidos.equals=" + UPDATED_QUANTIDADE_COCOS_VENDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosVendidosIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos in DEFAULT_QUANTIDADE_COCOS_VENDIDOS or UPDATED_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldBeFound(
            "quantidadeCocosVendidos.in=" + DEFAULT_QUANTIDADE_COCOS_VENDIDOS + "," + UPDATED_QUANTIDADE_COCOS_VENDIDOS
        );

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos equals to UPDATED_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosVendidos.in=" + UPDATED_QUANTIDADE_COCOS_VENDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosVendidosIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos is not null
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosVendidos.specified=true");

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos is null
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosVendidos.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosVendidosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos is greater than or equal to DEFAULT_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosVendidos.greaterThanOrEqual=" + DEFAULT_QUANTIDADE_COCOS_VENDIDOS);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos is greater than or equal to UPDATED_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosVendidos.greaterThanOrEqual=" + UPDATED_QUANTIDADE_COCOS_VENDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosVendidosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos is less than or equal to DEFAULT_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosVendidos.lessThanOrEqual=" + DEFAULT_QUANTIDADE_COCOS_VENDIDOS);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos is less than or equal to SMALLER_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosVendidos.lessThanOrEqual=" + SMALLER_QUANTIDADE_COCOS_VENDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosVendidosIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos is less than DEFAULT_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosVendidos.lessThan=" + DEFAULT_QUANTIDADE_COCOS_VENDIDOS);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos is less than UPDATED_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosVendidos.lessThan=" + UPDATED_QUANTIDADE_COCOS_VENDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocosVendidosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos is greater than DEFAULT_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocosVendidos.greaterThan=" + DEFAULT_QUANTIDADE_COCOS_VENDIDOS);

        // Get all the fechamentoCaixaList where quantidadeCocosVendidos is greater than SMALLER_QUANTIDADE_COCOS_VENDIDOS
        defaultFechamentoCaixaShouldBeFound("quantidadeCocosVendidos.greaterThan=" + SMALLER_QUANTIDADE_COCOS_VENDIDOS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocoSobrouIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou equals to DEFAULT_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldBeFound("quantidadeCocoSobrou.equals=" + DEFAULT_QUANTIDADE_COCO_SOBROU);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou equals to UPDATED_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocoSobrou.equals=" + UPDATED_QUANTIDADE_COCO_SOBROU);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocoSobrouIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou in DEFAULT_QUANTIDADE_COCO_SOBROU or UPDATED_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldBeFound(
            "quantidadeCocoSobrou.in=" + DEFAULT_QUANTIDADE_COCO_SOBROU + "," + UPDATED_QUANTIDADE_COCO_SOBROU
        );

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou equals to UPDATED_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocoSobrou.in=" + UPDATED_QUANTIDADE_COCO_SOBROU);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocoSobrouIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou is not null
        defaultFechamentoCaixaShouldBeFound("quantidadeCocoSobrou.specified=true");

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou is null
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocoSobrou.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocoSobrouIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou is greater than or equal to DEFAULT_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldBeFound("quantidadeCocoSobrou.greaterThanOrEqual=" + DEFAULT_QUANTIDADE_COCO_SOBROU);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou is greater than or equal to UPDATED_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocoSobrou.greaterThanOrEqual=" + UPDATED_QUANTIDADE_COCO_SOBROU);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocoSobrouIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou is less than or equal to DEFAULT_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldBeFound("quantidadeCocoSobrou.lessThanOrEqual=" + DEFAULT_QUANTIDADE_COCO_SOBROU);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou is less than or equal to SMALLER_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocoSobrou.lessThanOrEqual=" + SMALLER_QUANTIDADE_COCO_SOBROU);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocoSobrouIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou is less than DEFAULT_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocoSobrou.lessThan=" + DEFAULT_QUANTIDADE_COCO_SOBROU);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou is less than UPDATED_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldBeFound("quantidadeCocoSobrou.lessThan=" + UPDATED_QUANTIDADE_COCO_SOBROU);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByQuantidadeCocoSobrouIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou is greater than DEFAULT_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldNotBeFound("quantidadeCocoSobrou.greaterThan=" + DEFAULT_QUANTIDADE_COCO_SOBROU);

        // Get all the fechamentoCaixaList where quantidadeCocoSobrou is greater than SMALLER_QUANTIDADE_COCO_SOBROU
        defaultFechamentoCaixaShouldBeFound("quantidadeCocoSobrou.greaterThan=" + SMALLER_QUANTIDADE_COCO_SOBROU);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDivididoPorIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where divididoPor equals to DEFAULT_DIVIDIDO_POR
        defaultFechamentoCaixaShouldBeFound("divididoPor.equals=" + DEFAULT_DIVIDIDO_POR);

        // Get all the fechamentoCaixaList where divididoPor equals to UPDATED_DIVIDIDO_POR
        defaultFechamentoCaixaShouldNotBeFound("divididoPor.equals=" + UPDATED_DIVIDIDO_POR);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDivididoPorIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where divididoPor in DEFAULT_DIVIDIDO_POR or UPDATED_DIVIDIDO_POR
        defaultFechamentoCaixaShouldBeFound("divididoPor.in=" + DEFAULT_DIVIDIDO_POR + "," + UPDATED_DIVIDIDO_POR);

        // Get all the fechamentoCaixaList where divididoPor equals to UPDATED_DIVIDIDO_POR
        defaultFechamentoCaixaShouldNotBeFound("divididoPor.in=" + UPDATED_DIVIDIDO_POR);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDivididoPorIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where divididoPor is not null
        defaultFechamentoCaixaShouldBeFound("divididoPor.specified=true");

        // Get all the fechamentoCaixaList where divididoPor is null
        defaultFechamentoCaixaShouldNotBeFound("divididoPor.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDivididoPorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where divididoPor is greater than or equal to DEFAULT_DIVIDIDO_POR
        defaultFechamentoCaixaShouldBeFound("divididoPor.greaterThanOrEqual=" + DEFAULT_DIVIDIDO_POR);

        // Get all the fechamentoCaixaList where divididoPor is greater than or equal to UPDATED_DIVIDIDO_POR
        defaultFechamentoCaixaShouldNotBeFound("divididoPor.greaterThanOrEqual=" + UPDATED_DIVIDIDO_POR);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDivididoPorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where divididoPor is less than or equal to DEFAULT_DIVIDIDO_POR
        defaultFechamentoCaixaShouldBeFound("divididoPor.lessThanOrEqual=" + DEFAULT_DIVIDIDO_POR);

        // Get all the fechamentoCaixaList where divididoPor is less than or equal to SMALLER_DIVIDIDO_POR
        defaultFechamentoCaixaShouldNotBeFound("divididoPor.lessThanOrEqual=" + SMALLER_DIVIDIDO_POR);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDivididoPorIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where divididoPor is less than DEFAULT_DIVIDIDO_POR
        defaultFechamentoCaixaShouldNotBeFound("divididoPor.lessThan=" + DEFAULT_DIVIDIDO_POR);

        // Get all the fechamentoCaixaList where divididoPor is less than UPDATED_DIVIDIDO_POR
        defaultFechamentoCaixaShouldBeFound("divididoPor.lessThan=" + UPDATED_DIVIDIDO_POR);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByDivididoPorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where divididoPor is greater than DEFAULT_DIVIDIDO_POR
        defaultFechamentoCaixaShouldNotBeFound("divididoPor.greaterThan=" + DEFAULT_DIVIDIDO_POR);

        // Get all the fechamentoCaixaList where divididoPor is greater than SMALLER_DIVIDIDO_POR
        defaultFechamentoCaixaShouldBeFound("divididoPor.greaterThan=" + SMALLER_DIVIDIDO_POR);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCoco equals to DEFAULT_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldBeFound("valorTotalCoco.equals=" + DEFAULT_VALOR_TOTAL_COCO);

        // Get all the fechamentoCaixaList where valorTotalCoco equals to UPDATED_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCoco.equals=" + UPDATED_VALOR_TOTAL_COCO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCoco in DEFAULT_VALOR_TOTAL_COCO or UPDATED_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldBeFound("valorTotalCoco.in=" + DEFAULT_VALOR_TOTAL_COCO + "," + UPDATED_VALOR_TOTAL_COCO);

        // Get all the fechamentoCaixaList where valorTotalCoco equals to UPDATED_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCoco.in=" + UPDATED_VALOR_TOTAL_COCO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCoco is not null
        defaultFechamentoCaixaShouldBeFound("valorTotalCoco.specified=true");

        // Get all the fechamentoCaixaList where valorTotalCoco is null
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCoco.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCoco is greater than or equal to DEFAULT_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldBeFound("valorTotalCoco.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL_COCO);

        // Get all the fechamentoCaixaList where valorTotalCoco is greater than or equal to UPDATED_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCoco.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL_COCO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCoco is less than or equal to DEFAULT_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldBeFound("valorTotalCoco.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL_COCO);

        // Get all the fechamentoCaixaList where valorTotalCoco is less than or equal to SMALLER_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCoco.lessThanOrEqual=" + SMALLER_VALOR_TOTAL_COCO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCoco is less than DEFAULT_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCoco.lessThan=" + DEFAULT_VALOR_TOTAL_COCO);

        // Get all the fechamentoCaixaList where valorTotalCoco is less than UPDATED_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldBeFound("valorTotalCoco.lessThan=" + UPDATED_VALOR_TOTAL_COCO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCoco is greater than DEFAULT_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCoco.greaterThan=" + DEFAULT_VALOR_TOTAL_COCO);

        // Get all the fechamentoCaixaList where valorTotalCoco is greater than SMALLER_VALOR_TOTAL_COCO
        defaultFechamentoCaixaShouldBeFound("valorTotalCoco.greaterThan=" + SMALLER_VALOR_TOTAL_COCO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoPerdidoIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido equals to DEFAULT_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldBeFound("valorTotalCocoPerdido.equals=" + DEFAULT_VALOR_TOTAL_COCO_PERDIDO);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido equals to UPDATED_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCocoPerdido.equals=" + UPDATED_VALOR_TOTAL_COCO_PERDIDO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoPerdidoIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido in DEFAULT_VALOR_TOTAL_COCO_PERDIDO or UPDATED_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldBeFound(
            "valorTotalCocoPerdido.in=" + DEFAULT_VALOR_TOTAL_COCO_PERDIDO + "," + UPDATED_VALOR_TOTAL_COCO_PERDIDO
        );

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido equals to UPDATED_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCocoPerdido.in=" + UPDATED_VALOR_TOTAL_COCO_PERDIDO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoPerdidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido is not null
        defaultFechamentoCaixaShouldBeFound("valorTotalCocoPerdido.specified=true");

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido is null
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCocoPerdido.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoPerdidoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido is greater than or equal to DEFAULT_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldBeFound("valorTotalCocoPerdido.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL_COCO_PERDIDO);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido is greater than or equal to UPDATED_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCocoPerdido.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL_COCO_PERDIDO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoPerdidoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido is less than or equal to DEFAULT_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldBeFound("valorTotalCocoPerdido.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL_COCO_PERDIDO);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido is less than or equal to SMALLER_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCocoPerdido.lessThanOrEqual=" + SMALLER_VALOR_TOTAL_COCO_PERDIDO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoPerdidoIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido is less than DEFAULT_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCocoPerdido.lessThan=" + DEFAULT_VALOR_TOTAL_COCO_PERDIDO);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido is less than UPDATED_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldBeFound("valorTotalCocoPerdido.lessThan=" + UPDATED_VALOR_TOTAL_COCO_PERDIDO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalCocoPerdidoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido is greater than DEFAULT_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldNotBeFound("valorTotalCocoPerdido.greaterThan=" + DEFAULT_VALOR_TOTAL_COCO_PERDIDO);

        // Get all the fechamentoCaixaList where valorTotalCocoPerdido is greater than SMALLER_VALOR_TOTAL_COCO_PERDIDO
        defaultFechamentoCaixaShouldBeFound("valorTotalCocoPerdido.greaterThan=" + SMALLER_VALOR_TOTAL_COCO_PERDIDO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorPorPessoaIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorPorPessoa equals to DEFAULT_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldBeFound("valorPorPessoa.equals=" + DEFAULT_VALOR_POR_PESSOA);

        // Get all the fechamentoCaixaList where valorPorPessoa equals to UPDATED_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldNotBeFound("valorPorPessoa.equals=" + UPDATED_VALOR_POR_PESSOA);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorPorPessoaIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorPorPessoa in DEFAULT_VALOR_POR_PESSOA or UPDATED_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldBeFound("valorPorPessoa.in=" + DEFAULT_VALOR_POR_PESSOA + "," + UPDATED_VALOR_POR_PESSOA);

        // Get all the fechamentoCaixaList where valorPorPessoa equals to UPDATED_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldNotBeFound("valorPorPessoa.in=" + UPDATED_VALOR_POR_PESSOA);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorPorPessoaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorPorPessoa is not null
        defaultFechamentoCaixaShouldBeFound("valorPorPessoa.specified=true");

        // Get all the fechamentoCaixaList where valorPorPessoa is null
        defaultFechamentoCaixaShouldNotBeFound("valorPorPessoa.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorPorPessoaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorPorPessoa is greater than or equal to DEFAULT_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldBeFound("valorPorPessoa.greaterThanOrEqual=" + DEFAULT_VALOR_POR_PESSOA);

        // Get all the fechamentoCaixaList where valorPorPessoa is greater than or equal to UPDATED_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldNotBeFound("valorPorPessoa.greaterThanOrEqual=" + UPDATED_VALOR_POR_PESSOA);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorPorPessoaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorPorPessoa is less than or equal to DEFAULT_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldBeFound("valorPorPessoa.lessThanOrEqual=" + DEFAULT_VALOR_POR_PESSOA);

        // Get all the fechamentoCaixaList where valorPorPessoa is less than or equal to SMALLER_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldNotBeFound("valorPorPessoa.lessThanOrEqual=" + SMALLER_VALOR_POR_PESSOA);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorPorPessoaIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorPorPessoa is less than DEFAULT_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldNotBeFound("valorPorPessoa.lessThan=" + DEFAULT_VALOR_POR_PESSOA);

        // Get all the fechamentoCaixaList where valorPorPessoa is less than UPDATED_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldBeFound("valorPorPessoa.lessThan=" + UPDATED_VALOR_POR_PESSOA);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorPorPessoaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorPorPessoa is greater than DEFAULT_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldNotBeFound("valorPorPessoa.greaterThan=" + DEFAULT_VALOR_POR_PESSOA);

        // Get all the fechamentoCaixaList where valorPorPessoa is greater than SMALLER_VALOR_POR_PESSOA
        defaultFechamentoCaixaShouldBeFound("valorPorPessoa.greaterThan=" + SMALLER_VALOR_POR_PESSOA);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDespesasIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDespesas equals to DEFAULT_VALOR_DESPESAS
        defaultFechamentoCaixaShouldBeFound("valorDespesas.equals=" + DEFAULT_VALOR_DESPESAS);

        // Get all the fechamentoCaixaList where valorDespesas equals to UPDATED_VALOR_DESPESAS
        defaultFechamentoCaixaShouldNotBeFound("valorDespesas.equals=" + UPDATED_VALOR_DESPESAS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDespesasIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDespesas in DEFAULT_VALOR_DESPESAS or UPDATED_VALOR_DESPESAS
        defaultFechamentoCaixaShouldBeFound("valorDespesas.in=" + DEFAULT_VALOR_DESPESAS + "," + UPDATED_VALOR_DESPESAS);

        // Get all the fechamentoCaixaList where valorDespesas equals to UPDATED_VALOR_DESPESAS
        defaultFechamentoCaixaShouldNotBeFound("valorDespesas.in=" + UPDATED_VALOR_DESPESAS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDespesasIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDespesas is not null
        defaultFechamentoCaixaShouldBeFound("valorDespesas.specified=true");

        // Get all the fechamentoCaixaList where valorDespesas is null
        defaultFechamentoCaixaShouldNotBeFound("valorDespesas.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDespesasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDespesas is greater than or equal to DEFAULT_VALOR_DESPESAS
        defaultFechamentoCaixaShouldBeFound("valorDespesas.greaterThanOrEqual=" + DEFAULT_VALOR_DESPESAS);

        // Get all the fechamentoCaixaList where valorDespesas is greater than or equal to UPDATED_VALOR_DESPESAS
        defaultFechamentoCaixaShouldNotBeFound("valorDespesas.greaterThanOrEqual=" + UPDATED_VALOR_DESPESAS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDespesasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDespesas is less than or equal to DEFAULT_VALOR_DESPESAS
        defaultFechamentoCaixaShouldBeFound("valorDespesas.lessThanOrEqual=" + DEFAULT_VALOR_DESPESAS);

        // Get all the fechamentoCaixaList where valorDespesas is less than or equal to SMALLER_VALOR_DESPESAS
        defaultFechamentoCaixaShouldNotBeFound("valorDespesas.lessThanOrEqual=" + SMALLER_VALOR_DESPESAS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDespesasIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDespesas is less than DEFAULT_VALOR_DESPESAS
        defaultFechamentoCaixaShouldNotBeFound("valorDespesas.lessThan=" + DEFAULT_VALOR_DESPESAS);

        // Get all the fechamentoCaixaList where valorDespesas is less than UPDATED_VALOR_DESPESAS
        defaultFechamentoCaixaShouldBeFound("valorDespesas.lessThan=" + UPDATED_VALOR_DESPESAS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDespesasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDespesas is greater than DEFAULT_VALOR_DESPESAS
        defaultFechamentoCaixaShouldNotBeFound("valorDespesas.greaterThan=" + DEFAULT_VALOR_DESPESAS);

        // Get all the fechamentoCaixaList where valorDespesas is greater than SMALLER_VALOR_DESPESAS
        defaultFechamentoCaixaShouldBeFound("valorDespesas.greaterThan=" + SMALLER_VALOR_DESPESAS);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDinheiroIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDinheiro equals to DEFAULT_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldBeFound("valorDinheiro.equals=" + DEFAULT_VALOR_DINHEIRO);

        // Get all the fechamentoCaixaList where valorDinheiro equals to UPDATED_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldNotBeFound("valorDinheiro.equals=" + UPDATED_VALOR_DINHEIRO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDinheiroIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDinheiro in DEFAULT_VALOR_DINHEIRO or UPDATED_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldBeFound("valorDinheiro.in=" + DEFAULT_VALOR_DINHEIRO + "," + UPDATED_VALOR_DINHEIRO);

        // Get all the fechamentoCaixaList where valorDinheiro equals to UPDATED_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldNotBeFound("valorDinheiro.in=" + UPDATED_VALOR_DINHEIRO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDinheiroIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDinheiro is not null
        defaultFechamentoCaixaShouldBeFound("valorDinheiro.specified=true");

        // Get all the fechamentoCaixaList where valorDinheiro is null
        defaultFechamentoCaixaShouldNotBeFound("valorDinheiro.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDinheiroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDinheiro is greater than or equal to DEFAULT_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldBeFound("valorDinheiro.greaterThanOrEqual=" + DEFAULT_VALOR_DINHEIRO);

        // Get all the fechamentoCaixaList where valorDinheiro is greater than or equal to UPDATED_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldNotBeFound("valorDinheiro.greaterThanOrEqual=" + UPDATED_VALOR_DINHEIRO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDinheiroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDinheiro is less than or equal to DEFAULT_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldBeFound("valorDinheiro.lessThanOrEqual=" + DEFAULT_VALOR_DINHEIRO);

        // Get all the fechamentoCaixaList where valorDinheiro is less than or equal to SMALLER_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldNotBeFound("valorDinheiro.lessThanOrEqual=" + SMALLER_VALOR_DINHEIRO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDinheiroIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDinheiro is less than DEFAULT_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldNotBeFound("valorDinheiro.lessThan=" + DEFAULT_VALOR_DINHEIRO);

        // Get all the fechamentoCaixaList where valorDinheiro is less than UPDATED_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldBeFound("valorDinheiro.lessThan=" + UPDATED_VALOR_DINHEIRO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorDinheiroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorDinheiro is greater than DEFAULT_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldNotBeFound("valorDinheiro.greaterThan=" + DEFAULT_VALOR_DINHEIRO);

        // Get all the fechamentoCaixaList where valorDinheiro is greater than SMALLER_VALOR_DINHEIRO
        defaultFechamentoCaixaShouldBeFound("valorDinheiro.greaterThan=" + SMALLER_VALOR_DINHEIRO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorCartaoIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorCartao equals to DEFAULT_VALOR_CARTAO
        defaultFechamentoCaixaShouldBeFound("valorCartao.equals=" + DEFAULT_VALOR_CARTAO);

        // Get all the fechamentoCaixaList where valorCartao equals to UPDATED_VALOR_CARTAO
        defaultFechamentoCaixaShouldNotBeFound("valorCartao.equals=" + UPDATED_VALOR_CARTAO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorCartaoIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorCartao in DEFAULT_VALOR_CARTAO or UPDATED_VALOR_CARTAO
        defaultFechamentoCaixaShouldBeFound("valorCartao.in=" + DEFAULT_VALOR_CARTAO + "," + UPDATED_VALOR_CARTAO);

        // Get all the fechamentoCaixaList where valorCartao equals to UPDATED_VALOR_CARTAO
        defaultFechamentoCaixaShouldNotBeFound("valorCartao.in=" + UPDATED_VALOR_CARTAO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorCartaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorCartao is not null
        defaultFechamentoCaixaShouldBeFound("valorCartao.specified=true");

        // Get all the fechamentoCaixaList where valorCartao is null
        defaultFechamentoCaixaShouldNotBeFound("valorCartao.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorCartaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorCartao is greater than or equal to DEFAULT_VALOR_CARTAO
        defaultFechamentoCaixaShouldBeFound("valorCartao.greaterThanOrEqual=" + DEFAULT_VALOR_CARTAO);

        // Get all the fechamentoCaixaList where valorCartao is greater than or equal to UPDATED_VALOR_CARTAO
        defaultFechamentoCaixaShouldNotBeFound("valorCartao.greaterThanOrEqual=" + UPDATED_VALOR_CARTAO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorCartaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorCartao is less than or equal to DEFAULT_VALOR_CARTAO
        defaultFechamentoCaixaShouldBeFound("valorCartao.lessThanOrEqual=" + DEFAULT_VALOR_CARTAO);

        // Get all the fechamentoCaixaList where valorCartao is less than or equal to SMALLER_VALOR_CARTAO
        defaultFechamentoCaixaShouldNotBeFound("valorCartao.lessThanOrEqual=" + SMALLER_VALOR_CARTAO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorCartaoIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorCartao is less than DEFAULT_VALOR_CARTAO
        defaultFechamentoCaixaShouldNotBeFound("valorCartao.lessThan=" + DEFAULT_VALOR_CARTAO);

        // Get all the fechamentoCaixaList where valorCartao is less than UPDATED_VALOR_CARTAO
        defaultFechamentoCaixaShouldBeFound("valorCartao.lessThan=" + UPDATED_VALOR_CARTAO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorCartaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorCartao is greater than DEFAULT_VALOR_CARTAO
        defaultFechamentoCaixaShouldNotBeFound("valorCartao.greaterThan=" + DEFAULT_VALOR_CARTAO);

        // Get all the fechamentoCaixaList where valorCartao is greater than SMALLER_VALOR_CARTAO
        defaultFechamentoCaixaShouldBeFound("valorCartao.greaterThan=" + SMALLER_VALOR_CARTAO);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotal equals to DEFAULT_VALOR_TOTAL
        defaultFechamentoCaixaShouldBeFound("valorTotal.equals=" + DEFAULT_VALOR_TOTAL);

        // Get all the fechamentoCaixaList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultFechamentoCaixaShouldNotBeFound("valorTotal.equals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalIsInShouldWork() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotal in DEFAULT_VALOR_TOTAL or UPDATED_VALOR_TOTAL
        defaultFechamentoCaixaShouldBeFound("valorTotal.in=" + DEFAULT_VALOR_TOTAL + "," + UPDATED_VALOR_TOTAL);

        // Get all the fechamentoCaixaList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultFechamentoCaixaShouldNotBeFound("valorTotal.in=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotal is not null
        defaultFechamentoCaixaShouldBeFound("valorTotal.specified=true");

        // Get all the fechamentoCaixaList where valorTotal is null
        defaultFechamentoCaixaShouldNotBeFound("valorTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotal is greater than or equal to DEFAULT_VALOR_TOTAL
        defaultFechamentoCaixaShouldBeFound("valorTotal.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the fechamentoCaixaList where valorTotal is greater than or equal to UPDATED_VALOR_TOTAL
        defaultFechamentoCaixaShouldNotBeFound("valorTotal.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotal is less than or equal to DEFAULT_VALOR_TOTAL
        defaultFechamentoCaixaShouldBeFound("valorTotal.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the fechamentoCaixaList where valorTotal is less than or equal to SMALLER_VALOR_TOTAL
        defaultFechamentoCaixaShouldNotBeFound("valorTotal.lessThanOrEqual=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotal is less than DEFAULT_VALOR_TOTAL
        defaultFechamentoCaixaShouldNotBeFound("valorTotal.lessThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the fechamentoCaixaList where valorTotal is less than UPDATED_VALOR_TOTAL
        defaultFechamentoCaixaShouldBeFound("valorTotal.lessThan=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByValorTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        // Get all the fechamentoCaixaList where valorTotal is greater than DEFAULT_VALOR_TOTAL
        defaultFechamentoCaixaShouldNotBeFound("valorTotal.greaterThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the fechamentoCaixaList where valorTotal is greater than SMALLER_VALOR_TOTAL
        defaultFechamentoCaixaShouldBeFound("valorTotal.greaterThan=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllFechamentoCaixasByFechamentoCaixaDetalhesIsEqualToSomething() throws Exception {
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes;
        if (TestUtil.findAll(em, FechamentoCaixaDetalhes.class).isEmpty()) {
            fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);
            fechamentoCaixaDetalhes = FechamentoCaixaDetalhesResourceIT.createEntity(em);
        } else {
            fechamentoCaixaDetalhes = TestUtil.findAll(em, FechamentoCaixaDetalhes.class).get(0);
        }
        em.persist(fechamentoCaixaDetalhes);
        em.flush();
        fechamentoCaixa.setFechamentoCaixaDetalhes(fechamentoCaixaDetalhes);
        fechamentoCaixaDetalhes.setFechamentoCaixa(fechamentoCaixa);
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);
        Long fechamentoCaixaDetalhesId = fechamentoCaixaDetalhes.getId();
        // Get all the fechamentoCaixaList where fechamentoCaixaDetalhes equals to fechamentoCaixaDetalhesId
        defaultFechamentoCaixaShouldBeFound("fechamentoCaixaDetalhesId.equals=" + fechamentoCaixaDetalhesId);

        // Get all the fechamentoCaixaList where fechamentoCaixaDetalhes equals to (fechamentoCaixaDetalhesId + 1)
        defaultFechamentoCaixaShouldNotBeFound("fechamentoCaixaDetalhesId.equals=" + (fechamentoCaixaDetalhesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFechamentoCaixaShouldBeFound(String filter) throws Exception {
        restFechamentoCaixaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fechamentoCaixa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInicial").value(hasItem(sameInstant(DEFAULT_DATA_INICIAL))))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(sameInstant(DEFAULT_DATA_FINAL))))
            .andExpect(jsonPath("$.[*].quantidadeCocosPerdidos").value(hasItem(DEFAULT_QUANTIDADE_COCOS_PERDIDOS)))
            .andExpect(jsonPath("$.[*].quantidadeCocosVendidos").value(hasItem(DEFAULT_QUANTIDADE_COCOS_VENDIDOS)))
            .andExpect(jsonPath("$.[*].quantidadeCocoSobrou").value(hasItem(DEFAULT_QUANTIDADE_COCO_SOBROU)))
            .andExpect(jsonPath("$.[*].divididoPor").value(hasItem(DEFAULT_DIVIDIDO_POR)))
            .andExpect(jsonPath("$.[*].valorTotalCoco").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_COCO))))
            .andExpect(jsonPath("$.[*].valorTotalCocoPerdido").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_COCO_PERDIDO))))
            .andExpect(jsonPath("$.[*].valorPorPessoa").value(hasItem(sameNumber(DEFAULT_VALOR_POR_PESSOA))))
            .andExpect(jsonPath("$.[*].valorDespesas").value(hasItem(sameNumber(DEFAULT_VALOR_DESPESAS))))
            .andExpect(jsonPath("$.[*].valorDinheiro").value(hasItem(sameNumber(DEFAULT_VALOR_DINHEIRO))))
            .andExpect(jsonPath("$.[*].valorCartao").value(hasItem(sameNumber(DEFAULT_VALOR_CARTAO))))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL))));

        // Check, that the count call also returns 1
        restFechamentoCaixaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFechamentoCaixaShouldNotBeFound(String filter) throws Exception {
        restFechamentoCaixaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFechamentoCaixaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFechamentoCaixa() throws Exception {
        // Get the fechamentoCaixa
        restFechamentoCaixaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFechamentoCaixa() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        int databaseSizeBeforeUpdate = fechamentoCaixaRepository.findAll().size();

        // Update the fechamentoCaixa
        FechamentoCaixa updatedFechamentoCaixa = fechamentoCaixaRepository.findById(fechamentoCaixa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFechamentoCaixa are not directly saved in db
        em.detach(updatedFechamentoCaixa);
        updatedFechamentoCaixa
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .quantidadeCocosPerdidos(UPDATED_QUANTIDADE_COCOS_PERDIDOS)
            .quantidadeCocosVendidos(UPDATED_QUANTIDADE_COCOS_VENDIDOS)
            .quantidadeCocoSobrou(UPDATED_QUANTIDADE_COCO_SOBROU)
            .divididoPor(UPDATED_DIVIDIDO_POR)
            .valorTotalCoco(UPDATED_VALOR_TOTAL_COCO)
            .valorTotalCocoPerdido(UPDATED_VALOR_TOTAL_COCO_PERDIDO)
            .valorPorPessoa(UPDATED_VALOR_POR_PESSOA)
            .valorDespesas(UPDATED_VALOR_DESPESAS)
            .valorDinheiro(UPDATED_VALOR_DINHEIRO)
            .valorCartao(UPDATED_VALOR_CARTAO)
            .valorTotal(UPDATED_VALOR_TOTAL);
        FechamentoCaixaDTO fechamentoCaixaDTO = fechamentoCaixaMapper.toDto(updatedFechamentoCaixa);

        restFechamentoCaixaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fechamentoCaixaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDTO))
            )
            .andExpect(status().isOk());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeUpdate);
        FechamentoCaixa testFechamentoCaixa = fechamentoCaixaList.get(fechamentoCaixaList.size() - 1);
        assertThat(testFechamentoCaixa.getDataInicial()).isEqualTo(UPDATED_DATA_INICIAL);
        assertThat(testFechamentoCaixa.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
        assertThat(testFechamentoCaixa.getQuantidadeCocosPerdidos()).isEqualTo(UPDATED_QUANTIDADE_COCOS_PERDIDOS);
        assertThat(testFechamentoCaixa.getQuantidadeCocosVendidos()).isEqualTo(UPDATED_QUANTIDADE_COCOS_VENDIDOS);
        assertThat(testFechamentoCaixa.getQuantidadeCocoSobrou()).isEqualTo(UPDATED_QUANTIDADE_COCO_SOBROU);
        assertThat(testFechamentoCaixa.getDivididoPor()).isEqualTo(UPDATED_DIVIDIDO_POR);
        assertThat(testFechamentoCaixa.getValorTotalCoco()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_COCO);
        assertThat(testFechamentoCaixa.getValorTotalCocoPerdido()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_COCO_PERDIDO);
        assertThat(testFechamentoCaixa.getValorPorPessoa()).isEqualByComparingTo(UPDATED_VALOR_POR_PESSOA);
        assertThat(testFechamentoCaixa.getValorDespesas()).isEqualByComparingTo(UPDATED_VALOR_DESPESAS);
        assertThat(testFechamentoCaixa.getValorDinheiro()).isEqualByComparingTo(UPDATED_VALOR_DINHEIRO);
        assertThat(testFechamentoCaixa.getValorCartao()).isEqualByComparingTo(UPDATED_VALOR_CARTAO);
        assertThat(testFechamentoCaixa.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingFechamentoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaRepository.findAll().size();
        fechamentoCaixa.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixa
        FechamentoCaixaDTO fechamentoCaixaDTO = fechamentoCaixaMapper.toDto(fechamentoCaixa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFechamentoCaixaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fechamentoCaixaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFechamentoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaRepository.findAll().size();
        fechamentoCaixa.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixa
        FechamentoCaixaDTO fechamentoCaixaDTO = fechamentoCaixaMapper.toDto(fechamentoCaixa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFechamentoCaixaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFechamentoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaRepository.findAll().size();
        fechamentoCaixa.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixa
        FechamentoCaixaDTO fechamentoCaixaDTO = fechamentoCaixaMapper.toDto(fechamentoCaixa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFechamentoCaixaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFechamentoCaixaWithPatch() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        int databaseSizeBeforeUpdate = fechamentoCaixaRepository.findAll().size();

        // Update the fechamentoCaixa using partial update
        FechamentoCaixa partialUpdatedFechamentoCaixa = new FechamentoCaixa();
        partialUpdatedFechamentoCaixa.setId(fechamentoCaixa.getId());

        partialUpdatedFechamentoCaixa
            .quantidadeCocosPerdidos(UPDATED_QUANTIDADE_COCOS_PERDIDOS)
            .quantidadeCocoSobrou(UPDATED_QUANTIDADE_COCO_SOBROU)
            .divididoPor(UPDATED_DIVIDIDO_POR)
            .valorTotalCoco(UPDATED_VALOR_TOTAL_COCO)
            .valorDespesas(UPDATED_VALOR_DESPESAS)
            .valorTotal(UPDATED_VALOR_TOTAL);

        restFechamentoCaixaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFechamentoCaixa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFechamentoCaixa))
            )
            .andExpect(status().isOk());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeUpdate);
        FechamentoCaixa testFechamentoCaixa = fechamentoCaixaList.get(fechamentoCaixaList.size() - 1);
        assertThat(testFechamentoCaixa.getDataInicial()).isEqualTo(DEFAULT_DATA_INICIAL);
        assertThat(testFechamentoCaixa.getDataFinal()).isEqualTo(DEFAULT_DATA_FINAL);
        assertThat(testFechamentoCaixa.getQuantidadeCocosPerdidos()).isEqualTo(UPDATED_QUANTIDADE_COCOS_PERDIDOS);
        assertThat(testFechamentoCaixa.getQuantidadeCocosVendidos()).isEqualTo(DEFAULT_QUANTIDADE_COCOS_VENDIDOS);
        assertThat(testFechamentoCaixa.getQuantidadeCocoSobrou()).isEqualTo(UPDATED_QUANTIDADE_COCO_SOBROU);
        assertThat(testFechamentoCaixa.getDivididoPor()).isEqualTo(UPDATED_DIVIDIDO_POR);
        assertThat(testFechamentoCaixa.getValorTotalCoco()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_COCO);
        assertThat(testFechamentoCaixa.getValorTotalCocoPerdido()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_COCO_PERDIDO);
        assertThat(testFechamentoCaixa.getValorPorPessoa()).isEqualByComparingTo(DEFAULT_VALOR_POR_PESSOA);
        assertThat(testFechamentoCaixa.getValorDespesas()).isEqualByComparingTo(UPDATED_VALOR_DESPESAS);
        assertThat(testFechamentoCaixa.getValorDinheiro()).isEqualByComparingTo(DEFAULT_VALOR_DINHEIRO);
        assertThat(testFechamentoCaixa.getValorCartao()).isEqualByComparingTo(DEFAULT_VALOR_CARTAO);
        assertThat(testFechamentoCaixa.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateFechamentoCaixaWithPatch() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        int databaseSizeBeforeUpdate = fechamentoCaixaRepository.findAll().size();

        // Update the fechamentoCaixa using partial update
        FechamentoCaixa partialUpdatedFechamentoCaixa = new FechamentoCaixa();
        partialUpdatedFechamentoCaixa.setId(fechamentoCaixa.getId());

        partialUpdatedFechamentoCaixa
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .quantidadeCocosPerdidos(UPDATED_QUANTIDADE_COCOS_PERDIDOS)
            .quantidadeCocosVendidos(UPDATED_QUANTIDADE_COCOS_VENDIDOS)
            .quantidadeCocoSobrou(UPDATED_QUANTIDADE_COCO_SOBROU)
            .divididoPor(UPDATED_DIVIDIDO_POR)
            .valorTotalCoco(UPDATED_VALOR_TOTAL_COCO)
            .valorTotalCocoPerdido(UPDATED_VALOR_TOTAL_COCO_PERDIDO)
            .valorPorPessoa(UPDATED_VALOR_POR_PESSOA)
            .valorDespesas(UPDATED_VALOR_DESPESAS)
            .valorDinheiro(UPDATED_VALOR_DINHEIRO)
            .valorCartao(UPDATED_VALOR_CARTAO)
            .valorTotal(UPDATED_VALOR_TOTAL);

        restFechamentoCaixaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFechamentoCaixa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFechamentoCaixa))
            )
            .andExpect(status().isOk());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeUpdate);
        FechamentoCaixa testFechamentoCaixa = fechamentoCaixaList.get(fechamentoCaixaList.size() - 1);
        assertThat(testFechamentoCaixa.getDataInicial()).isEqualTo(UPDATED_DATA_INICIAL);
        assertThat(testFechamentoCaixa.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
        assertThat(testFechamentoCaixa.getQuantidadeCocosPerdidos()).isEqualTo(UPDATED_QUANTIDADE_COCOS_PERDIDOS);
        assertThat(testFechamentoCaixa.getQuantidadeCocosVendidos()).isEqualTo(UPDATED_QUANTIDADE_COCOS_VENDIDOS);
        assertThat(testFechamentoCaixa.getQuantidadeCocoSobrou()).isEqualTo(UPDATED_QUANTIDADE_COCO_SOBROU);
        assertThat(testFechamentoCaixa.getDivididoPor()).isEqualTo(UPDATED_DIVIDIDO_POR);
        assertThat(testFechamentoCaixa.getValorTotalCoco()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_COCO);
        assertThat(testFechamentoCaixa.getValorTotalCocoPerdido()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_COCO_PERDIDO);
        assertThat(testFechamentoCaixa.getValorPorPessoa()).isEqualByComparingTo(UPDATED_VALOR_POR_PESSOA);
        assertThat(testFechamentoCaixa.getValorDespesas()).isEqualByComparingTo(UPDATED_VALOR_DESPESAS);
        assertThat(testFechamentoCaixa.getValorDinheiro()).isEqualByComparingTo(UPDATED_VALOR_DINHEIRO);
        assertThat(testFechamentoCaixa.getValorCartao()).isEqualByComparingTo(UPDATED_VALOR_CARTAO);
        assertThat(testFechamentoCaixa.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingFechamentoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaRepository.findAll().size();
        fechamentoCaixa.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixa
        FechamentoCaixaDTO fechamentoCaixaDTO = fechamentoCaixaMapper.toDto(fechamentoCaixa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFechamentoCaixaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fechamentoCaixaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFechamentoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaRepository.findAll().size();
        fechamentoCaixa.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixa
        FechamentoCaixaDTO fechamentoCaixaDTO = fechamentoCaixaMapper.toDto(fechamentoCaixa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFechamentoCaixaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFechamentoCaixa() throws Exception {
        int databaseSizeBeforeUpdate = fechamentoCaixaRepository.findAll().size();
        fechamentoCaixa.setId(longCount.incrementAndGet());

        // Create the FechamentoCaixa
        FechamentoCaixaDTO fechamentoCaixaDTO = fechamentoCaixaMapper.toDto(fechamentoCaixa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFechamentoCaixaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fechamentoCaixaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FechamentoCaixa in the database
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFechamentoCaixa() throws Exception {
        // Initialize the database
        fechamentoCaixaRepository.saveAndFlush(fechamentoCaixa);

        int databaseSizeBeforeDelete = fechamentoCaixaRepository.findAll().size();

        // Delete the fechamentoCaixa
        restFechamentoCaixaMockMvc
            .perform(delete(ENTITY_API_URL_ID, fechamentoCaixa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FechamentoCaixa> fechamentoCaixaList = fechamentoCaixaRepository.findAll();
        assertThat(fechamentoCaixaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
