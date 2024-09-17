package br.com.lisetech.cocoverde.web.rest;

import static br.com.lisetech.cocoverde.web.rest.TestUtil.sameInstant;
import static br.com.lisetech.cocoverde.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.Estoque;
import br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes;
import br.com.lisetech.cocoverde.domain.Frente;
import br.com.lisetech.cocoverde.domain.Imagem;
import br.com.lisetech.cocoverde.domain.SaidaFinanceira;
import br.com.lisetech.cocoverde.domain.enumeration.MetodoPagamento;
import br.com.lisetech.cocoverde.domain.enumeration.ResponsavelPagamento;
import br.com.lisetech.cocoverde.domain.enumeration.StatusPagamento;
import br.com.lisetech.cocoverde.repository.SaidaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.dto.SaidaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.SaidaFinanceiraMapper;
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
 * Integration tests for the {@link SaidaFinanceiraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaidaFinanceiraResourceIT {

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL = new BigDecimal(1 - 1);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final MetodoPagamento DEFAULT_METODO_PAGAMENTO = MetodoPagamento.DINHEIRO;
    private static final MetodoPagamento UPDATED_METODO_PAGAMENTO = MetodoPagamento.CARTAO_DEBITO;

    private static final StatusPagamento DEFAULT_STATUS_PAGAMENTO = StatusPagamento.PAGO;
    private static final StatusPagamento UPDATED_STATUS_PAGAMENTO = StatusPagamento.NAO_PAGO;

    private static final ResponsavelPagamento DEFAULT_RESPONSAVEL_PAGAMENTO = ResponsavelPagamento.BARRACA;
    private static final ResponsavelPagamento UPDATED_RESPONSAVEL_PAGAMENTO = ResponsavelPagamento.CHEFE;

    private static final String ENTITY_API_URL = "/api/saida-financeiras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SaidaFinanceiraRepository saidaFinanceiraRepository;

    @Autowired
    private SaidaFinanceiraMapper saidaFinanceiraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaidaFinanceiraMockMvc;

    private SaidaFinanceira saidaFinanceira;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaidaFinanceira createEntity(EntityManager em) {
        SaidaFinanceira saidaFinanceira = new SaidaFinanceira()
            .data(DEFAULT_DATA)
            .valorTotal(DEFAULT_VALOR_TOTAL)
            .descricao(DEFAULT_DESCRICAO)
            .metodoPagamento(DEFAULT_METODO_PAGAMENTO)
            .statusPagamento(DEFAULT_STATUS_PAGAMENTO)
            .responsavelPagamento(DEFAULT_RESPONSAVEL_PAGAMENTO);
        return saidaFinanceira;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaidaFinanceira createUpdatedEntity(EntityManager em) {
        SaidaFinanceira saidaFinanceira = new SaidaFinanceira()
            .data(UPDATED_DATA)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .descricao(UPDATED_DESCRICAO)
            .metodoPagamento(UPDATED_METODO_PAGAMENTO)
            .statusPagamento(UPDATED_STATUS_PAGAMENTO)
            .responsavelPagamento(UPDATED_RESPONSAVEL_PAGAMENTO);
        return saidaFinanceira;
    }

    @BeforeEach
    public void initTest() {
        saidaFinanceira = createEntity(em);
    }

    @Test
    @Transactional
    void createSaidaFinanceira() throws Exception {
        int databaseSizeBeforeCreate = saidaFinanceiraRepository.findAll().size();
        // Create the SaidaFinanceira
        SaidaFinanceiraDTO saidaFinanceiraDTO = saidaFinanceiraMapper.toDto(saidaFinanceira);
        restSaidaFinanceiraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saidaFinanceiraDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeCreate + 1);
        SaidaFinanceira testSaidaFinanceira = saidaFinanceiraList.get(saidaFinanceiraList.size() - 1);
        assertThat(testSaidaFinanceira.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testSaidaFinanceira.getValorTotal()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL);
        assertThat(testSaidaFinanceira.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testSaidaFinanceira.getMetodoPagamento()).isEqualTo(DEFAULT_METODO_PAGAMENTO);
        assertThat(testSaidaFinanceira.getStatusPagamento()).isEqualTo(DEFAULT_STATUS_PAGAMENTO);
        assertThat(testSaidaFinanceira.getResponsavelPagamento()).isEqualTo(DEFAULT_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void createSaidaFinanceiraWithExistingId() throws Exception {
        // Create the SaidaFinanceira with an existing ID
        saidaFinanceira.setId(1L);
        SaidaFinanceiraDTO saidaFinanceiraDTO = saidaFinanceiraMapper.toDto(saidaFinanceira);

        int databaseSizeBeforeCreate = saidaFinanceiraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaidaFinanceiraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saidaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceiras() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList
        restSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saidaFinanceira.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].metodoPagamento").value(hasItem(DEFAULT_METODO_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].statusPagamento").value(hasItem(DEFAULT_STATUS_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].responsavelPagamento").value(hasItem(DEFAULT_RESPONSAVEL_PAGAMENTO.toString())));
    }

    @Test
    @Transactional
    void getSaidaFinanceira() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get the saidaFinanceira
        restSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL_ID, saidaFinanceira.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saidaFinanceira.getId().intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.valorTotal").value(sameNumber(DEFAULT_VALOR_TOTAL)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.metodoPagamento").value(DEFAULT_METODO_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.statusPagamento").value(DEFAULT_STATUS_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.responsavelPagamento").value(DEFAULT_RESPONSAVEL_PAGAMENTO.toString()));
    }

    @Test
    @Transactional
    void getSaidaFinanceirasByIdFiltering() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        Long id = saidaFinanceira.getId();

        defaultSaidaFinanceiraShouldBeFound("id.equals=" + id);
        defaultSaidaFinanceiraShouldNotBeFound("id.notEquals=" + id);

        defaultSaidaFinanceiraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSaidaFinanceiraShouldNotBeFound("id.greaterThan=" + id);

        defaultSaidaFinanceiraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSaidaFinanceiraShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where data equals to DEFAULT_DATA
        defaultSaidaFinanceiraShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the saidaFinanceiraList where data equals to UPDATED_DATA
        defaultSaidaFinanceiraShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDataIsInShouldWork() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where data in DEFAULT_DATA or UPDATED_DATA
        defaultSaidaFinanceiraShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the saidaFinanceiraList where data equals to UPDATED_DATA
        defaultSaidaFinanceiraShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where data is not null
        defaultSaidaFinanceiraShouldBeFound("data.specified=true");

        // Get all the saidaFinanceiraList where data is null
        defaultSaidaFinanceiraShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where data is greater than or equal to DEFAULT_DATA
        defaultSaidaFinanceiraShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the saidaFinanceiraList where data is greater than or equal to UPDATED_DATA
        defaultSaidaFinanceiraShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where data is less than or equal to DEFAULT_DATA
        defaultSaidaFinanceiraShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the saidaFinanceiraList where data is less than or equal to SMALLER_DATA
        defaultSaidaFinanceiraShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where data is less than DEFAULT_DATA
        defaultSaidaFinanceiraShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the saidaFinanceiraList where data is less than UPDATED_DATA
        defaultSaidaFinanceiraShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where data is greater than DEFAULT_DATA
        defaultSaidaFinanceiraShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the saidaFinanceiraList where data is greater than SMALLER_DATA
        defaultSaidaFinanceiraShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByValorTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where valorTotal equals to DEFAULT_VALOR_TOTAL
        defaultSaidaFinanceiraShouldBeFound("valorTotal.equals=" + DEFAULT_VALOR_TOTAL);

        // Get all the saidaFinanceiraList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultSaidaFinanceiraShouldNotBeFound("valorTotal.equals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByValorTotalIsInShouldWork() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where valorTotal in DEFAULT_VALOR_TOTAL or UPDATED_VALOR_TOTAL
        defaultSaidaFinanceiraShouldBeFound("valorTotal.in=" + DEFAULT_VALOR_TOTAL + "," + UPDATED_VALOR_TOTAL);

        // Get all the saidaFinanceiraList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultSaidaFinanceiraShouldNotBeFound("valorTotal.in=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByValorTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where valorTotal is not null
        defaultSaidaFinanceiraShouldBeFound("valorTotal.specified=true");

        // Get all the saidaFinanceiraList where valorTotal is null
        defaultSaidaFinanceiraShouldNotBeFound("valorTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByValorTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where valorTotal is greater than or equal to DEFAULT_VALOR_TOTAL
        defaultSaidaFinanceiraShouldBeFound("valorTotal.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the saidaFinanceiraList where valorTotal is greater than or equal to UPDATED_VALOR_TOTAL
        defaultSaidaFinanceiraShouldNotBeFound("valorTotal.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByValorTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where valorTotal is less than or equal to DEFAULT_VALOR_TOTAL
        defaultSaidaFinanceiraShouldBeFound("valorTotal.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the saidaFinanceiraList where valorTotal is less than or equal to SMALLER_VALOR_TOTAL
        defaultSaidaFinanceiraShouldNotBeFound("valorTotal.lessThanOrEqual=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByValorTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where valorTotal is less than DEFAULT_VALOR_TOTAL
        defaultSaidaFinanceiraShouldNotBeFound("valorTotal.lessThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the saidaFinanceiraList where valorTotal is less than UPDATED_VALOR_TOTAL
        defaultSaidaFinanceiraShouldBeFound("valorTotal.lessThan=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByValorTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where valorTotal is greater than DEFAULT_VALOR_TOTAL
        defaultSaidaFinanceiraShouldNotBeFound("valorTotal.greaterThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the saidaFinanceiraList where valorTotal is greater than SMALLER_VALOR_TOTAL
        defaultSaidaFinanceiraShouldBeFound("valorTotal.greaterThan=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where descricao equals to DEFAULT_DESCRICAO
        defaultSaidaFinanceiraShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the saidaFinanceiraList where descricao equals to UPDATED_DESCRICAO
        defaultSaidaFinanceiraShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultSaidaFinanceiraShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the saidaFinanceiraList where descricao equals to UPDATED_DESCRICAO
        defaultSaidaFinanceiraShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where descricao is not null
        defaultSaidaFinanceiraShouldBeFound("descricao.specified=true");

        // Get all the saidaFinanceiraList where descricao is null
        defaultSaidaFinanceiraShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where descricao contains DEFAULT_DESCRICAO
        defaultSaidaFinanceiraShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the saidaFinanceiraList where descricao contains UPDATED_DESCRICAO
        defaultSaidaFinanceiraShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where descricao does not contain DEFAULT_DESCRICAO
        defaultSaidaFinanceiraShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the saidaFinanceiraList where descricao does not contain UPDATED_DESCRICAO
        defaultSaidaFinanceiraShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByMetodoPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where metodoPagamento equals to DEFAULT_METODO_PAGAMENTO
        defaultSaidaFinanceiraShouldBeFound("metodoPagamento.equals=" + DEFAULT_METODO_PAGAMENTO);

        // Get all the saidaFinanceiraList where metodoPagamento equals to UPDATED_METODO_PAGAMENTO
        defaultSaidaFinanceiraShouldNotBeFound("metodoPagamento.equals=" + UPDATED_METODO_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByMetodoPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where metodoPagamento in DEFAULT_METODO_PAGAMENTO or UPDATED_METODO_PAGAMENTO
        defaultSaidaFinanceiraShouldBeFound("metodoPagamento.in=" + DEFAULT_METODO_PAGAMENTO + "," + UPDATED_METODO_PAGAMENTO);

        // Get all the saidaFinanceiraList where metodoPagamento equals to UPDATED_METODO_PAGAMENTO
        defaultSaidaFinanceiraShouldNotBeFound("metodoPagamento.in=" + UPDATED_METODO_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByMetodoPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where metodoPagamento is not null
        defaultSaidaFinanceiraShouldBeFound("metodoPagamento.specified=true");

        // Get all the saidaFinanceiraList where metodoPagamento is null
        defaultSaidaFinanceiraShouldNotBeFound("metodoPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByStatusPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where statusPagamento equals to DEFAULT_STATUS_PAGAMENTO
        defaultSaidaFinanceiraShouldBeFound("statusPagamento.equals=" + DEFAULT_STATUS_PAGAMENTO);

        // Get all the saidaFinanceiraList where statusPagamento equals to UPDATED_STATUS_PAGAMENTO
        defaultSaidaFinanceiraShouldNotBeFound("statusPagamento.equals=" + UPDATED_STATUS_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByStatusPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where statusPagamento in DEFAULT_STATUS_PAGAMENTO or UPDATED_STATUS_PAGAMENTO
        defaultSaidaFinanceiraShouldBeFound("statusPagamento.in=" + DEFAULT_STATUS_PAGAMENTO + "," + UPDATED_STATUS_PAGAMENTO);

        // Get all the saidaFinanceiraList where statusPagamento equals to UPDATED_STATUS_PAGAMENTO
        defaultSaidaFinanceiraShouldNotBeFound("statusPagamento.in=" + UPDATED_STATUS_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByStatusPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where statusPagamento is not null
        defaultSaidaFinanceiraShouldBeFound("statusPagamento.specified=true");

        // Get all the saidaFinanceiraList where statusPagamento is null
        defaultSaidaFinanceiraShouldNotBeFound("statusPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByResponsavelPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where responsavelPagamento equals to DEFAULT_RESPONSAVEL_PAGAMENTO
        defaultSaidaFinanceiraShouldBeFound("responsavelPagamento.equals=" + DEFAULT_RESPONSAVEL_PAGAMENTO);

        // Get all the saidaFinanceiraList where responsavelPagamento equals to UPDATED_RESPONSAVEL_PAGAMENTO
        defaultSaidaFinanceiraShouldNotBeFound("responsavelPagamento.equals=" + UPDATED_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByResponsavelPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where responsavelPagamento in DEFAULT_RESPONSAVEL_PAGAMENTO or UPDATED_RESPONSAVEL_PAGAMENTO
        defaultSaidaFinanceiraShouldBeFound(
            "responsavelPagamento.in=" + DEFAULT_RESPONSAVEL_PAGAMENTO + "," + UPDATED_RESPONSAVEL_PAGAMENTO
        );

        // Get all the saidaFinanceiraList where responsavelPagamento equals to UPDATED_RESPONSAVEL_PAGAMENTO
        defaultSaidaFinanceiraShouldNotBeFound("responsavelPagamento.in=" + UPDATED_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByResponsavelPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        // Get all the saidaFinanceiraList where responsavelPagamento is not null
        defaultSaidaFinanceiraShouldBeFound("responsavelPagamento.specified=true");

        // Get all the saidaFinanceiraList where responsavelPagamento is null
        defaultSaidaFinanceiraShouldNotBeFound("responsavelPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByEstoqueIsEqualToSomething() throws Exception {
        Estoque estoque;
        if (TestUtil.findAll(em, Estoque.class).isEmpty()) {
            saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);
            estoque = EstoqueResourceIT.createEntity(em);
        } else {
            estoque = TestUtil.findAll(em, Estoque.class).get(0);
        }
        em.persist(estoque);
        em.flush();
        saidaFinanceira.setEstoque(estoque);
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);
        Long estoqueId = estoque.getId();
        // Get all the saidaFinanceiraList where estoque equals to estoqueId
        defaultSaidaFinanceiraShouldBeFound("estoqueId.equals=" + estoqueId);

        // Get all the saidaFinanceiraList where estoque equals to (estoqueId + 1)
        defaultSaidaFinanceiraShouldNotBeFound("estoqueId.equals=" + (estoqueId + 1));
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByFrenteIsEqualToSomething() throws Exception {
        Frente frente;
        if (TestUtil.findAll(em, Frente.class).isEmpty()) {
            saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);
            frente = FrenteResourceIT.createEntity(em);
        } else {
            frente = TestUtil.findAll(em, Frente.class).get(0);
        }
        em.persist(frente);
        em.flush();
        saidaFinanceira.setFrente(frente);
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);
        Long frenteId = frente.getId();
        // Get all the saidaFinanceiraList where frente equals to frenteId
        defaultSaidaFinanceiraShouldBeFound("frenteId.equals=" + frenteId);

        // Get all the saidaFinanceiraList where frente equals to (frenteId + 1)
        defaultSaidaFinanceiraShouldNotBeFound("frenteId.equals=" + (frenteId + 1));
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByFechamentoCaixaDetalhesIsEqualToSomething() throws Exception {
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes;
        if (TestUtil.findAll(em, FechamentoCaixaDetalhes.class).isEmpty()) {
            saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);
            fechamentoCaixaDetalhes = FechamentoCaixaDetalhesResourceIT.createEntity(em);
        } else {
            fechamentoCaixaDetalhes = TestUtil.findAll(em, FechamentoCaixaDetalhes.class).get(0);
        }
        em.persist(fechamentoCaixaDetalhes);
        em.flush();
        saidaFinanceira.setFechamentoCaixaDetalhes(fechamentoCaixaDetalhes);
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);
        Long fechamentoCaixaDetalhesId = fechamentoCaixaDetalhes.getId();
        // Get all the saidaFinanceiraList where fechamentoCaixaDetalhes equals to fechamentoCaixaDetalhesId
        defaultSaidaFinanceiraShouldBeFound("fechamentoCaixaDetalhesId.equals=" + fechamentoCaixaDetalhesId);

        // Get all the saidaFinanceiraList where fechamentoCaixaDetalhes equals to (fechamentoCaixaDetalhesId + 1)
        defaultSaidaFinanceiraShouldNotBeFound("fechamentoCaixaDetalhesId.equals=" + (fechamentoCaixaDetalhesId + 1));
    }

    @Test
    @Transactional
    void getAllSaidaFinanceirasByImagemIsEqualToSomething() throws Exception {
        Imagem imagem;
        if (TestUtil.findAll(em, Imagem.class).isEmpty()) {
            saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);
            imagem = ImagemResourceIT.createEntity(em);
        } else {
            imagem = TestUtil.findAll(em, Imagem.class).get(0);
        }
        em.persist(imagem);
        em.flush();
        saidaFinanceira.addImagem(imagem);
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);
        Long imagemId = imagem.getId();
        // Get all the saidaFinanceiraList where imagem equals to imagemId
        defaultSaidaFinanceiraShouldBeFound("imagemId.equals=" + imagemId);

        // Get all the saidaFinanceiraList where imagem equals to (imagemId + 1)
        defaultSaidaFinanceiraShouldNotBeFound("imagemId.equals=" + (imagemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSaidaFinanceiraShouldBeFound(String filter) throws Exception {
        restSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saidaFinanceira.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].metodoPagamento").value(hasItem(DEFAULT_METODO_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].statusPagamento").value(hasItem(DEFAULT_STATUS_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].responsavelPagamento").value(hasItem(DEFAULT_RESPONSAVEL_PAGAMENTO.toString())));

        // Check, that the count call also returns 1
        restSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSaidaFinanceiraShouldNotBeFound(String filter) throws Exception {
        restSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSaidaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSaidaFinanceira() throws Exception {
        // Get the saidaFinanceira
        restSaidaFinanceiraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSaidaFinanceira() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        int databaseSizeBeforeUpdate = saidaFinanceiraRepository.findAll().size();

        // Update the saidaFinanceira
        SaidaFinanceira updatedSaidaFinanceira = saidaFinanceiraRepository.findById(saidaFinanceira.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSaidaFinanceira are not directly saved in db
        em.detach(updatedSaidaFinanceira);
        updatedSaidaFinanceira
            .data(UPDATED_DATA)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .descricao(UPDATED_DESCRICAO)
            .metodoPagamento(UPDATED_METODO_PAGAMENTO)
            .statusPagamento(UPDATED_STATUS_PAGAMENTO)
            .responsavelPagamento(UPDATED_RESPONSAVEL_PAGAMENTO);
        SaidaFinanceiraDTO saidaFinanceiraDTO = saidaFinanceiraMapper.toDto(updatedSaidaFinanceira);

        restSaidaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saidaFinanceiraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saidaFinanceiraDTO))
            )
            .andExpect(status().isOk());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        SaidaFinanceira testSaidaFinanceira = saidaFinanceiraList.get(saidaFinanceiraList.size() - 1);
        assertThat(testSaidaFinanceira.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testSaidaFinanceira.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
        assertThat(testSaidaFinanceira.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testSaidaFinanceira.getMetodoPagamento()).isEqualTo(UPDATED_METODO_PAGAMENTO);
        assertThat(testSaidaFinanceira.getStatusPagamento()).isEqualTo(UPDATED_STATUS_PAGAMENTO);
        assertThat(testSaidaFinanceira.getResponsavelPagamento()).isEqualTo(UPDATED_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void putNonExistingSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = saidaFinanceiraRepository.findAll().size();
        saidaFinanceira.setId(longCount.incrementAndGet());

        // Create the SaidaFinanceira
        SaidaFinanceiraDTO saidaFinanceiraDTO = saidaFinanceiraMapper.toDto(saidaFinanceira);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaidaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saidaFinanceiraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saidaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = saidaFinanceiraRepository.findAll().size();
        saidaFinanceira.setId(longCount.incrementAndGet());

        // Create the SaidaFinanceira
        SaidaFinanceiraDTO saidaFinanceiraDTO = saidaFinanceiraMapper.toDto(saidaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaidaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saidaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = saidaFinanceiraRepository.findAll().size();
        saidaFinanceira.setId(longCount.incrementAndGet());

        // Create the SaidaFinanceira
        SaidaFinanceiraDTO saidaFinanceiraDTO = saidaFinanceiraMapper.toDto(saidaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaidaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saidaFinanceiraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaidaFinanceiraWithPatch() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        int databaseSizeBeforeUpdate = saidaFinanceiraRepository.findAll().size();

        // Update the saidaFinanceira using partial update
        SaidaFinanceira partialUpdatedSaidaFinanceira = new SaidaFinanceira();
        partialUpdatedSaidaFinanceira.setId(saidaFinanceira.getId());

        partialUpdatedSaidaFinanceira.descricao(UPDATED_DESCRICAO).statusPagamento(UPDATED_STATUS_PAGAMENTO);

        restSaidaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaidaFinanceira.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaidaFinanceira))
            )
            .andExpect(status().isOk());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        SaidaFinanceira testSaidaFinanceira = saidaFinanceiraList.get(saidaFinanceiraList.size() - 1);
        assertThat(testSaidaFinanceira.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testSaidaFinanceira.getValorTotal()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL);
        assertThat(testSaidaFinanceira.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testSaidaFinanceira.getMetodoPagamento()).isEqualTo(DEFAULT_METODO_PAGAMENTO);
        assertThat(testSaidaFinanceira.getStatusPagamento()).isEqualTo(UPDATED_STATUS_PAGAMENTO);
        assertThat(testSaidaFinanceira.getResponsavelPagamento()).isEqualTo(DEFAULT_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void fullUpdateSaidaFinanceiraWithPatch() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        int databaseSizeBeforeUpdate = saidaFinanceiraRepository.findAll().size();

        // Update the saidaFinanceira using partial update
        SaidaFinanceira partialUpdatedSaidaFinanceira = new SaidaFinanceira();
        partialUpdatedSaidaFinanceira.setId(saidaFinanceira.getId());

        partialUpdatedSaidaFinanceira
            .data(UPDATED_DATA)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .descricao(UPDATED_DESCRICAO)
            .metodoPagamento(UPDATED_METODO_PAGAMENTO)
            .statusPagamento(UPDATED_STATUS_PAGAMENTO)
            .responsavelPagamento(UPDATED_RESPONSAVEL_PAGAMENTO);

        restSaidaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaidaFinanceira.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaidaFinanceira))
            )
            .andExpect(status().isOk());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        SaidaFinanceira testSaidaFinanceira = saidaFinanceiraList.get(saidaFinanceiraList.size() - 1);
        assertThat(testSaidaFinanceira.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testSaidaFinanceira.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
        assertThat(testSaidaFinanceira.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testSaidaFinanceira.getMetodoPagamento()).isEqualTo(UPDATED_METODO_PAGAMENTO);
        assertThat(testSaidaFinanceira.getStatusPagamento()).isEqualTo(UPDATED_STATUS_PAGAMENTO);
        assertThat(testSaidaFinanceira.getResponsavelPagamento()).isEqualTo(UPDATED_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = saidaFinanceiraRepository.findAll().size();
        saidaFinanceira.setId(longCount.incrementAndGet());

        // Create the SaidaFinanceira
        SaidaFinanceiraDTO saidaFinanceiraDTO = saidaFinanceiraMapper.toDto(saidaFinanceira);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaidaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saidaFinanceiraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saidaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = saidaFinanceiraRepository.findAll().size();
        saidaFinanceira.setId(longCount.incrementAndGet());

        // Create the SaidaFinanceira
        SaidaFinanceiraDTO saidaFinanceiraDTO = saidaFinanceiraMapper.toDto(saidaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaidaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saidaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSaidaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = saidaFinanceiraRepository.findAll().size();
        saidaFinanceira.setId(longCount.incrementAndGet());

        // Create the SaidaFinanceira
        SaidaFinanceiraDTO saidaFinanceiraDTO = saidaFinanceiraMapper.toDto(saidaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaidaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saidaFinanceiraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaidaFinanceira in the database
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSaidaFinanceira() throws Exception {
        // Initialize the database
        saidaFinanceiraRepository.saveAndFlush(saidaFinanceira);

        int databaseSizeBeforeDelete = saidaFinanceiraRepository.findAll().size();

        // Delete the saidaFinanceira
        restSaidaFinanceiraMockMvc
            .perform(delete(ENTITY_API_URL_ID, saidaFinanceira.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SaidaFinanceira> saidaFinanceiraList = saidaFinanceiraRepository.findAll();
        assertThat(saidaFinanceiraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
