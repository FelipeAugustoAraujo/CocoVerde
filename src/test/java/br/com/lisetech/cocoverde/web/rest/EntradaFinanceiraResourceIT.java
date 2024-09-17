package br.com.lisetech.cocoverde.web.rest;

import static br.com.lisetech.cocoverde.web.rest.TestUtil.sameInstant;
import static br.com.lisetech.cocoverde.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.DetalhesEntradaFinanceira;
import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.domain.Estoque;
import br.com.lisetech.cocoverde.domain.FechamentoCaixaDetalhes;
import br.com.lisetech.cocoverde.domain.Fornecedor;
import br.com.lisetech.cocoverde.domain.Frente;
import br.com.lisetech.cocoverde.domain.Imagem;
import br.com.lisetech.cocoverde.domain.enumeration.MetodoPagamento;
import br.com.lisetech.cocoverde.domain.enumeration.ResponsavelPagamento;
import br.com.lisetech.cocoverde.domain.enumeration.StatusPagamento;
import br.com.lisetech.cocoverde.repository.EntradaFinanceiraRepository;
import br.com.lisetech.cocoverde.service.dto.EntradaFinanceiraDTO;
import br.com.lisetech.cocoverde.service.mapper.EntradaFinanceiraMapper;
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
 * Integration tests for the {@link EntradaFinanceiraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntradaFinanceiraResourceIT {

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

    private static final String ENTITY_API_URL = "/api/entrada-financeiras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntradaFinanceiraRepository entradaFinanceiraRepository;

    @Autowired
    private EntradaFinanceiraMapper entradaFinanceiraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntradaFinanceiraMockMvc;

    private EntradaFinanceira entradaFinanceira;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntradaFinanceira createEntity(EntityManager em) {
        EntradaFinanceira entradaFinanceira = new EntradaFinanceira()
            .data(DEFAULT_DATA)
            .valorTotal(DEFAULT_VALOR_TOTAL)
            .descricao(DEFAULT_DESCRICAO)
            .metodoPagamento(DEFAULT_METODO_PAGAMENTO)
            .statusPagamento(DEFAULT_STATUS_PAGAMENTO)
            .responsavelPagamento(DEFAULT_RESPONSAVEL_PAGAMENTO);
        return entradaFinanceira;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntradaFinanceira createUpdatedEntity(EntityManager em) {
        EntradaFinanceira entradaFinanceira = new EntradaFinanceira()
            .data(UPDATED_DATA)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .descricao(UPDATED_DESCRICAO)
            .metodoPagamento(UPDATED_METODO_PAGAMENTO)
            .statusPagamento(UPDATED_STATUS_PAGAMENTO)
            .responsavelPagamento(UPDATED_RESPONSAVEL_PAGAMENTO);
        return entradaFinanceira;
    }

    @BeforeEach
    public void initTest() {
        entradaFinanceira = createEntity(em);
    }

    @Test
    @Transactional
    void createEntradaFinanceira() throws Exception {
        int databaseSizeBeforeCreate = entradaFinanceiraRepository.findAll().size();
        // Create the EntradaFinanceira
        EntradaFinanceiraDTO entradaFinanceiraDTO = entradaFinanceiraMapper.toDto(entradaFinanceira);
        restEntradaFinanceiraMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entradaFinanceiraDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeCreate + 1);
        EntradaFinanceira testEntradaFinanceira = entradaFinanceiraList.get(entradaFinanceiraList.size() - 1);
        assertThat(testEntradaFinanceira.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testEntradaFinanceira.getValorTotal()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL);
        assertThat(testEntradaFinanceira.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testEntradaFinanceira.getMetodoPagamento()).isEqualTo(DEFAULT_METODO_PAGAMENTO);
        assertThat(testEntradaFinanceira.getStatusPagamento()).isEqualTo(DEFAULT_STATUS_PAGAMENTO);
        assertThat(testEntradaFinanceira.getResponsavelPagamento()).isEqualTo(DEFAULT_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void createEntradaFinanceiraWithExistingId() throws Exception {
        // Create the EntradaFinanceira with an existing ID
        entradaFinanceira.setId(1L);
        EntradaFinanceiraDTO entradaFinanceiraDTO = entradaFinanceiraMapper.toDto(entradaFinanceira);

        int databaseSizeBeforeCreate = entradaFinanceiraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntradaFinanceiraMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entradaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceiras() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList
        restEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entradaFinanceira.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].metodoPagamento").value(hasItem(DEFAULT_METODO_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].statusPagamento").value(hasItem(DEFAULT_STATUS_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].responsavelPagamento").value(hasItem(DEFAULT_RESPONSAVEL_PAGAMENTO.toString())));
    }

    @Test
    @Transactional
    void getEntradaFinanceira() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get the entradaFinanceira
        restEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL_ID, entradaFinanceira.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entradaFinanceira.getId().intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.valorTotal").value(sameNumber(DEFAULT_VALOR_TOTAL)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.metodoPagamento").value(DEFAULT_METODO_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.statusPagamento").value(DEFAULT_STATUS_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.responsavelPagamento").value(DEFAULT_RESPONSAVEL_PAGAMENTO.toString()));
    }

    @Test
    @Transactional
    void getEntradaFinanceirasByIdFiltering() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        Long id = entradaFinanceira.getId();

        defaultEntradaFinanceiraShouldBeFound("id.equals=" + id);
        defaultEntradaFinanceiraShouldNotBeFound("id.notEquals=" + id);

        defaultEntradaFinanceiraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEntradaFinanceiraShouldNotBeFound("id.greaterThan=" + id);

        defaultEntradaFinanceiraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEntradaFinanceiraShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where data equals to DEFAULT_DATA
        defaultEntradaFinanceiraShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the entradaFinanceiraList where data equals to UPDATED_DATA
        defaultEntradaFinanceiraShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDataIsInShouldWork() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where data in DEFAULT_DATA or UPDATED_DATA
        defaultEntradaFinanceiraShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the entradaFinanceiraList where data equals to UPDATED_DATA
        defaultEntradaFinanceiraShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where data is not null
        defaultEntradaFinanceiraShouldBeFound("data.specified=true");

        // Get all the entradaFinanceiraList where data is null
        defaultEntradaFinanceiraShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where data is greater than or equal to DEFAULT_DATA
        defaultEntradaFinanceiraShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the entradaFinanceiraList where data is greater than or equal to UPDATED_DATA
        defaultEntradaFinanceiraShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where data is less than or equal to DEFAULT_DATA
        defaultEntradaFinanceiraShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the entradaFinanceiraList where data is less than or equal to SMALLER_DATA
        defaultEntradaFinanceiraShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where data is less than DEFAULT_DATA
        defaultEntradaFinanceiraShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the entradaFinanceiraList where data is less than UPDATED_DATA
        defaultEntradaFinanceiraShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where data is greater than DEFAULT_DATA
        defaultEntradaFinanceiraShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the entradaFinanceiraList where data is greater than SMALLER_DATA
        defaultEntradaFinanceiraShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByValorTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where valorTotal equals to DEFAULT_VALOR_TOTAL
        defaultEntradaFinanceiraShouldBeFound("valorTotal.equals=" + DEFAULT_VALOR_TOTAL);

        // Get all the entradaFinanceiraList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultEntradaFinanceiraShouldNotBeFound("valorTotal.equals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByValorTotalIsInShouldWork() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where valorTotal in DEFAULT_VALOR_TOTAL or UPDATED_VALOR_TOTAL
        defaultEntradaFinanceiraShouldBeFound("valorTotal.in=" + DEFAULT_VALOR_TOTAL + "," + UPDATED_VALOR_TOTAL);

        // Get all the entradaFinanceiraList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultEntradaFinanceiraShouldNotBeFound("valorTotal.in=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByValorTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where valorTotal is not null
        defaultEntradaFinanceiraShouldBeFound("valorTotal.specified=true");

        // Get all the entradaFinanceiraList where valorTotal is null
        defaultEntradaFinanceiraShouldNotBeFound("valorTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByValorTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where valorTotal is greater than or equal to DEFAULT_VALOR_TOTAL
        defaultEntradaFinanceiraShouldBeFound("valorTotal.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the entradaFinanceiraList where valorTotal is greater than or equal to UPDATED_VALOR_TOTAL
        defaultEntradaFinanceiraShouldNotBeFound("valorTotal.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByValorTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where valorTotal is less than or equal to DEFAULT_VALOR_TOTAL
        defaultEntradaFinanceiraShouldBeFound("valorTotal.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the entradaFinanceiraList where valorTotal is less than or equal to SMALLER_VALOR_TOTAL
        defaultEntradaFinanceiraShouldNotBeFound("valorTotal.lessThanOrEqual=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByValorTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where valorTotal is less than DEFAULT_VALOR_TOTAL
        defaultEntradaFinanceiraShouldNotBeFound("valorTotal.lessThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the entradaFinanceiraList where valorTotal is less than UPDATED_VALOR_TOTAL
        defaultEntradaFinanceiraShouldBeFound("valorTotal.lessThan=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByValorTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where valorTotal is greater than DEFAULT_VALOR_TOTAL
        defaultEntradaFinanceiraShouldNotBeFound("valorTotal.greaterThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the entradaFinanceiraList where valorTotal is greater than SMALLER_VALOR_TOTAL
        defaultEntradaFinanceiraShouldBeFound("valorTotal.greaterThan=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where descricao equals to DEFAULT_DESCRICAO
        defaultEntradaFinanceiraShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the entradaFinanceiraList where descricao equals to UPDATED_DESCRICAO
        defaultEntradaFinanceiraShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultEntradaFinanceiraShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the entradaFinanceiraList where descricao equals to UPDATED_DESCRICAO
        defaultEntradaFinanceiraShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where descricao is not null
        defaultEntradaFinanceiraShouldBeFound("descricao.specified=true");

        // Get all the entradaFinanceiraList where descricao is null
        defaultEntradaFinanceiraShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where descricao contains DEFAULT_DESCRICAO
        defaultEntradaFinanceiraShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the entradaFinanceiraList where descricao contains UPDATED_DESCRICAO
        defaultEntradaFinanceiraShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where descricao does not contain DEFAULT_DESCRICAO
        defaultEntradaFinanceiraShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the entradaFinanceiraList where descricao does not contain UPDATED_DESCRICAO
        defaultEntradaFinanceiraShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByMetodoPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where metodoPagamento equals to DEFAULT_METODO_PAGAMENTO
        defaultEntradaFinanceiraShouldBeFound("metodoPagamento.equals=" + DEFAULT_METODO_PAGAMENTO);

        // Get all the entradaFinanceiraList where metodoPagamento equals to UPDATED_METODO_PAGAMENTO
        defaultEntradaFinanceiraShouldNotBeFound("metodoPagamento.equals=" + UPDATED_METODO_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByMetodoPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where metodoPagamento in DEFAULT_METODO_PAGAMENTO or UPDATED_METODO_PAGAMENTO
        defaultEntradaFinanceiraShouldBeFound("metodoPagamento.in=" + DEFAULT_METODO_PAGAMENTO + "," + UPDATED_METODO_PAGAMENTO);

        // Get all the entradaFinanceiraList where metodoPagamento equals to UPDATED_METODO_PAGAMENTO
        defaultEntradaFinanceiraShouldNotBeFound("metodoPagamento.in=" + UPDATED_METODO_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByMetodoPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where metodoPagamento is not null
        defaultEntradaFinanceiraShouldBeFound("metodoPagamento.specified=true");

        // Get all the entradaFinanceiraList where metodoPagamento is null
        defaultEntradaFinanceiraShouldNotBeFound("metodoPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByStatusPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where statusPagamento equals to DEFAULT_STATUS_PAGAMENTO
        defaultEntradaFinanceiraShouldBeFound("statusPagamento.equals=" + DEFAULT_STATUS_PAGAMENTO);

        // Get all the entradaFinanceiraList where statusPagamento equals to UPDATED_STATUS_PAGAMENTO
        defaultEntradaFinanceiraShouldNotBeFound("statusPagamento.equals=" + UPDATED_STATUS_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByStatusPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where statusPagamento in DEFAULT_STATUS_PAGAMENTO or UPDATED_STATUS_PAGAMENTO
        defaultEntradaFinanceiraShouldBeFound("statusPagamento.in=" + DEFAULT_STATUS_PAGAMENTO + "," + UPDATED_STATUS_PAGAMENTO);

        // Get all the entradaFinanceiraList where statusPagamento equals to UPDATED_STATUS_PAGAMENTO
        defaultEntradaFinanceiraShouldNotBeFound("statusPagamento.in=" + UPDATED_STATUS_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByStatusPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where statusPagamento is not null
        defaultEntradaFinanceiraShouldBeFound("statusPagamento.specified=true");

        // Get all the entradaFinanceiraList where statusPagamento is null
        defaultEntradaFinanceiraShouldNotBeFound("statusPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByResponsavelPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where responsavelPagamento equals to DEFAULT_RESPONSAVEL_PAGAMENTO
        defaultEntradaFinanceiraShouldBeFound("responsavelPagamento.equals=" + DEFAULT_RESPONSAVEL_PAGAMENTO);

        // Get all the entradaFinanceiraList where responsavelPagamento equals to UPDATED_RESPONSAVEL_PAGAMENTO
        defaultEntradaFinanceiraShouldNotBeFound("responsavelPagamento.equals=" + UPDATED_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByResponsavelPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where responsavelPagamento in DEFAULT_RESPONSAVEL_PAGAMENTO or UPDATED_RESPONSAVEL_PAGAMENTO
        defaultEntradaFinanceiraShouldBeFound(
            "responsavelPagamento.in=" + DEFAULT_RESPONSAVEL_PAGAMENTO + "," + UPDATED_RESPONSAVEL_PAGAMENTO
        );

        // Get all the entradaFinanceiraList where responsavelPagamento equals to UPDATED_RESPONSAVEL_PAGAMENTO
        defaultEntradaFinanceiraShouldNotBeFound("responsavelPagamento.in=" + UPDATED_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByResponsavelPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        // Get all the entradaFinanceiraList where responsavelPagamento is not null
        defaultEntradaFinanceiraShouldBeFound("responsavelPagamento.specified=true");

        // Get all the entradaFinanceiraList where responsavelPagamento is null
        defaultEntradaFinanceiraShouldNotBeFound("responsavelPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByFornecedorIsEqualToSomething() throws Exception {
        Fornecedor fornecedor;
        if (TestUtil.findAll(em, Fornecedor.class).isEmpty()) {
            entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
            fornecedor = FornecedorResourceIT.createEntity(em);
        } else {
            fornecedor = TestUtil.findAll(em, Fornecedor.class).get(0);
        }
        em.persist(fornecedor);
        em.flush();
        entradaFinanceira.setFornecedor(fornecedor);
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
        Long fornecedorId = fornecedor.getId();
        // Get all the entradaFinanceiraList where fornecedor equals to fornecedorId
        defaultEntradaFinanceiraShouldBeFound("fornecedorId.equals=" + fornecedorId);

        // Get all the entradaFinanceiraList where fornecedor equals to (fornecedorId + 1)
        defaultEntradaFinanceiraShouldNotBeFound("fornecedorId.equals=" + (fornecedorId + 1));
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByEstoqueIsEqualToSomething() throws Exception {
        Estoque estoque;
        if (TestUtil.findAll(em, Estoque.class).isEmpty()) {
            entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
            estoque = EstoqueResourceIT.createEntity(em);
        } else {
            estoque = TestUtil.findAll(em, Estoque.class).get(0);
        }
        em.persist(estoque);
        em.flush();
        entradaFinanceira.setEstoque(estoque);
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
        Long estoqueId = estoque.getId();
        // Get all the entradaFinanceiraList where estoque equals to estoqueId
        defaultEntradaFinanceiraShouldBeFound("estoqueId.equals=" + estoqueId);

        // Get all the entradaFinanceiraList where estoque equals to (estoqueId + 1)
        defaultEntradaFinanceiraShouldNotBeFound("estoqueId.equals=" + (estoqueId + 1));
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByFrenteIsEqualToSomething() throws Exception {
        Frente frente;
        if (TestUtil.findAll(em, Frente.class).isEmpty()) {
            entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
            frente = FrenteResourceIT.createEntity(em);
        } else {
            frente = TestUtil.findAll(em, Frente.class).get(0);
        }
        em.persist(frente);
        em.flush();
        entradaFinanceira.setFrente(frente);
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
        Long frenteId = frente.getId();
        // Get all the entradaFinanceiraList where frente equals to frenteId
        defaultEntradaFinanceiraShouldBeFound("frenteId.equals=" + frenteId);

        // Get all the entradaFinanceiraList where frente equals to (frenteId + 1)
        defaultEntradaFinanceiraShouldNotBeFound("frenteId.equals=" + (frenteId + 1));
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByFechamentoCaixaDetalhesIsEqualToSomething() throws Exception {
        FechamentoCaixaDetalhes fechamentoCaixaDetalhes;
        if (TestUtil.findAll(em, FechamentoCaixaDetalhes.class).isEmpty()) {
            entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
            fechamentoCaixaDetalhes = FechamentoCaixaDetalhesResourceIT.createEntity(em);
        } else {
            fechamentoCaixaDetalhes = TestUtil.findAll(em, FechamentoCaixaDetalhes.class).get(0);
        }
        em.persist(fechamentoCaixaDetalhes);
        em.flush();
        entradaFinanceira.setFechamentoCaixaDetalhes(fechamentoCaixaDetalhes);
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
        Long fechamentoCaixaDetalhesId = fechamentoCaixaDetalhes.getId();
        // Get all the entradaFinanceiraList where fechamentoCaixaDetalhes equals to fechamentoCaixaDetalhesId
        defaultEntradaFinanceiraShouldBeFound("fechamentoCaixaDetalhesId.equals=" + fechamentoCaixaDetalhesId);

        // Get all the entradaFinanceiraList where fechamentoCaixaDetalhes equals to (fechamentoCaixaDetalhesId + 1)
        defaultEntradaFinanceiraShouldNotBeFound("fechamentoCaixaDetalhesId.equals=" + (fechamentoCaixaDetalhesId + 1));
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByDetalhesEntradaFinanceiraIsEqualToSomething() throws Exception {
        DetalhesEntradaFinanceira detalhesEntradaFinanceira;
        if (TestUtil.findAll(em, DetalhesEntradaFinanceira.class).isEmpty()) {
            entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
            detalhesEntradaFinanceira = DetalhesEntradaFinanceiraResourceIT.createEntity(em);
        } else {
            detalhesEntradaFinanceira = TestUtil.findAll(em, DetalhesEntradaFinanceira.class).get(0);
        }
        em.persist(detalhesEntradaFinanceira);
        em.flush();
        entradaFinanceira.addDetalhesEntradaFinanceira(detalhesEntradaFinanceira);
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
        Long detalhesEntradaFinanceiraId = detalhesEntradaFinanceira.getId();
        // Get all the entradaFinanceiraList where detalhesEntradaFinanceira equals to detalhesEntradaFinanceiraId
        defaultEntradaFinanceiraShouldBeFound("detalhesEntradaFinanceiraId.equals=" + detalhesEntradaFinanceiraId);

        // Get all the entradaFinanceiraList where detalhesEntradaFinanceira equals to (detalhesEntradaFinanceiraId + 1)
        defaultEntradaFinanceiraShouldNotBeFound("detalhesEntradaFinanceiraId.equals=" + (detalhesEntradaFinanceiraId + 1));
    }

    @Test
    @Transactional
    void getAllEntradaFinanceirasByImagemIsEqualToSomething() throws Exception {
        Imagem imagem;
        if (TestUtil.findAll(em, Imagem.class).isEmpty()) {
            entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
            imagem = ImagemResourceIT.createEntity(em);
        } else {
            imagem = TestUtil.findAll(em, Imagem.class).get(0);
        }
        em.persist(imagem);
        em.flush();
        entradaFinanceira.addImagem(imagem);
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);
        Long imagemId = imagem.getId();
        // Get all the entradaFinanceiraList where imagem equals to imagemId
        defaultEntradaFinanceiraShouldBeFound("imagemId.equals=" + imagemId);

        // Get all the entradaFinanceiraList where imagem equals to (imagemId + 1)
        defaultEntradaFinanceiraShouldNotBeFound("imagemId.equals=" + (imagemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEntradaFinanceiraShouldBeFound(String filter) throws Exception {
        restEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entradaFinanceira.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].metodoPagamento").value(hasItem(DEFAULT_METODO_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].statusPagamento").value(hasItem(DEFAULT_STATUS_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].responsavelPagamento").value(hasItem(DEFAULT_RESPONSAVEL_PAGAMENTO.toString())));

        // Check, that the count call also returns 1
        restEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEntradaFinanceiraShouldNotBeFound(String filter) throws Exception {
        restEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEntradaFinanceiraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEntradaFinanceira() throws Exception {
        // Get the entradaFinanceira
        restEntradaFinanceiraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEntradaFinanceira() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        int databaseSizeBeforeUpdate = entradaFinanceiraRepository.findAll().size();

        // Update the entradaFinanceira
        EntradaFinanceira updatedEntradaFinanceira = entradaFinanceiraRepository.findById(entradaFinanceira.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEntradaFinanceira are not directly saved in db
        em.detach(updatedEntradaFinanceira);
        updatedEntradaFinanceira
            .data(UPDATED_DATA)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .descricao(UPDATED_DESCRICAO)
            .metodoPagamento(UPDATED_METODO_PAGAMENTO)
            .statusPagamento(UPDATED_STATUS_PAGAMENTO)
            .responsavelPagamento(UPDATED_RESPONSAVEL_PAGAMENTO);
        EntradaFinanceiraDTO entradaFinanceiraDTO = entradaFinanceiraMapper.toDto(updatedEntradaFinanceira);

        restEntradaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entradaFinanceiraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entradaFinanceiraDTO))
            )
            .andExpect(status().isOk());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        EntradaFinanceira testEntradaFinanceira = entradaFinanceiraList.get(entradaFinanceiraList.size() - 1);
        assertThat(testEntradaFinanceira.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testEntradaFinanceira.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
        assertThat(testEntradaFinanceira.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testEntradaFinanceira.getMetodoPagamento()).isEqualTo(UPDATED_METODO_PAGAMENTO);
        assertThat(testEntradaFinanceira.getStatusPagamento()).isEqualTo(UPDATED_STATUS_PAGAMENTO);
        assertThat(testEntradaFinanceira.getResponsavelPagamento()).isEqualTo(UPDATED_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void putNonExistingEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = entradaFinanceiraRepository.findAll().size();
        entradaFinanceira.setId(longCount.incrementAndGet());

        // Create the EntradaFinanceira
        EntradaFinanceiraDTO entradaFinanceiraDTO = entradaFinanceiraMapper.toDto(entradaFinanceira);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntradaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entradaFinanceiraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entradaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = entradaFinanceiraRepository.findAll().size();
        entradaFinanceira.setId(longCount.incrementAndGet());

        // Create the EntradaFinanceira
        EntradaFinanceiraDTO entradaFinanceiraDTO = entradaFinanceiraMapper.toDto(entradaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntradaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entradaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = entradaFinanceiraRepository.findAll().size();
        entradaFinanceira.setId(longCount.incrementAndGet());

        // Create the EntradaFinanceira
        EntradaFinanceiraDTO entradaFinanceiraDTO = entradaFinanceiraMapper.toDto(entradaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntradaFinanceiraMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entradaFinanceiraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntradaFinanceiraWithPatch() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        int databaseSizeBeforeUpdate = entradaFinanceiraRepository.findAll().size();

        // Update the entradaFinanceira using partial update
        EntradaFinanceira partialUpdatedEntradaFinanceira = new EntradaFinanceira();
        partialUpdatedEntradaFinanceira.setId(entradaFinanceira.getId());

        partialUpdatedEntradaFinanceira
            .data(UPDATED_DATA)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .descricao(UPDATED_DESCRICAO)
            .metodoPagamento(UPDATED_METODO_PAGAMENTO)
            .responsavelPagamento(UPDATED_RESPONSAVEL_PAGAMENTO);

        restEntradaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntradaFinanceira.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntradaFinanceira))
            )
            .andExpect(status().isOk());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        EntradaFinanceira testEntradaFinanceira = entradaFinanceiraList.get(entradaFinanceiraList.size() - 1);
        assertThat(testEntradaFinanceira.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testEntradaFinanceira.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
        assertThat(testEntradaFinanceira.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testEntradaFinanceira.getMetodoPagamento()).isEqualTo(UPDATED_METODO_PAGAMENTO);
        assertThat(testEntradaFinanceira.getStatusPagamento()).isEqualTo(DEFAULT_STATUS_PAGAMENTO);
        assertThat(testEntradaFinanceira.getResponsavelPagamento()).isEqualTo(UPDATED_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void fullUpdateEntradaFinanceiraWithPatch() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        int databaseSizeBeforeUpdate = entradaFinanceiraRepository.findAll().size();

        // Update the entradaFinanceira using partial update
        EntradaFinanceira partialUpdatedEntradaFinanceira = new EntradaFinanceira();
        partialUpdatedEntradaFinanceira.setId(entradaFinanceira.getId());

        partialUpdatedEntradaFinanceira
            .data(UPDATED_DATA)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .descricao(UPDATED_DESCRICAO)
            .metodoPagamento(UPDATED_METODO_PAGAMENTO)
            .statusPagamento(UPDATED_STATUS_PAGAMENTO)
            .responsavelPagamento(UPDATED_RESPONSAVEL_PAGAMENTO);

        restEntradaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntradaFinanceira.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntradaFinanceira))
            )
            .andExpect(status().isOk());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
        EntradaFinanceira testEntradaFinanceira = entradaFinanceiraList.get(entradaFinanceiraList.size() - 1);
        assertThat(testEntradaFinanceira.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testEntradaFinanceira.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
        assertThat(testEntradaFinanceira.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testEntradaFinanceira.getMetodoPagamento()).isEqualTo(UPDATED_METODO_PAGAMENTO);
        assertThat(testEntradaFinanceira.getStatusPagamento()).isEqualTo(UPDATED_STATUS_PAGAMENTO);
        assertThat(testEntradaFinanceira.getResponsavelPagamento()).isEqualTo(UPDATED_RESPONSAVEL_PAGAMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = entradaFinanceiraRepository.findAll().size();
        entradaFinanceira.setId(longCount.incrementAndGet());

        // Create the EntradaFinanceira
        EntradaFinanceiraDTO entradaFinanceiraDTO = entradaFinanceiraMapper.toDto(entradaFinanceira);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntradaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entradaFinanceiraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entradaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = entradaFinanceiraRepository.findAll().size();
        entradaFinanceira.setId(longCount.incrementAndGet());

        // Create the EntradaFinanceira
        EntradaFinanceiraDTO entradaFinanceiraDTO = entradaFinanceiraMapper.toDto(entradaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntradaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entradaFinanceiraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntradaFinanceira() throws Exception {
        int databaseSizeBeforeUpdate = entradaFinanceiraRepository.findAll().size();
        entradaFinanceira.setId(longCount.incrementAndGet());

        // Create the EntradaFinanceira
        EntradaFinanceiraDTO entradaFinanceiraDTO = entradaFinanceiraMapper.toDto(entradaFinanceira);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntradaFinanceiraMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entradaFinanceiraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntradaFinanceira in the database
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntradaFinanceira() throws Exception {
        // Initialize the database
        entradaFinanceiraRepository.saveAndFlush(entradaFinanceira);

        int databaseSizeBeforeDelete = entradaFinanceiraRepository.findAll().size();

        // Delete the entradaFinanceira
        restEntradaFinanceiraMockMvc
            .perform(delete(ENTITY_API_URL_ID, entradaFinanceira.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntradaFinanceira> entradaFinanceiraList = entradaFinanceiraRepository.findAll();
        assertThat(entradaFinanceiraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
