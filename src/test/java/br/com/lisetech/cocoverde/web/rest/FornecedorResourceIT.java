package br.com.lisetech.cocoverde.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.Endereco;
import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.domain.Fornecedor;
import br.com.lisetech.cocoverde.domain.Produto;
import br.com.lisetech.cocoverde.repository.FornecedorRepository;
import br.com.lisetech.cocoverde.service.FornecedorService;
import br.com.lisetech.cocoverde.service.dto.FornecedorDTO;
import br.com.lisetech.cocoverde.service.mapper.FornecedorMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FornecedorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FornecedorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICADOR = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICADOR = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_CADASTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CADASTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/fornecedors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Mock
    private FornecedorRepository fornecedorRepositoryMock;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    @Mock
    private FornecedorService fornecedorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFornecedorMockMvc;

    private Fornecedor fornecedor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .nome(DEFAULT_NOME)
            .identificador(DEFAULT_IDENTIFICADOR)
            .telefone(DEFAULT_TELEFONE)
            .dataCadastro(DEFAULT_DATA_CADASTRO);
        return fornecedor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createUpdatedEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .nome(UPDATED_NOME)
            .identificador(UPDATED_IDENTIFICADOR)
            .telefone(UPDATED_TELEFONE)
            .dataCadastro(UPDATED_DATA_CADASTRO);
        return fornecedor;
    }

    @BeforeEach
    public void initTest() {
        fornecedor = createEntity(em);
    }

    @Test
    @Transactional
    void createFornecedor() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();
        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);
        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate + 1);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFornecedor.getIdentificador()).isEqualTo(DEFAULT_IDENTIFICADOR);
        assertThat(testFornecedor.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testFornecedor.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void createFornecedorWithExistingId() throws Exception {
        // Create the Fornecedor with an existing ID
        fornecedor.setId(1L);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setNome(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setTelefone(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataCadastroIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setDataCadastro(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFornecedors() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList
        restFornecedorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].identificador").value(hasItem(DEFAULT_IDENTIFICADOR)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFornecedorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(fornecedorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFornecedorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(fornecedorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFornecedorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fornecedorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFornecedorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(fornecedorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get the fornecedor
        restFornecedorMockMvc
            .perform(get(ENTITY_API_URL_ID, fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.identificador").value(DEFAULT_IDENTIFICADOR))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()));
    }

    @Test
    @Transactional
    void getFornecedorsByIdFiltering() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        Long id = fornecedor.getId();

        defaultFornecedorShouldBeFound("id.equals=" + id);
        defaultFornecedorShouldNotBeFound("id.notEquals=" + id);

        defaultFornecedorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFornecedorShouldNotBeFound("id.greaterThan=" + id);

        defaultFornecedorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFornecedorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFornecedorsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome equals to DEFAULT_NOME
        defaultFornecedorShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the fornecedorList where nome equals to UPDATED_NOME
        defaultFornecedorShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFornecedorsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultFornecedorShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the fornecedorList where nome equals to UPDATED_NOME
        defaultFornecedorShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFornecedorsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome is not null
        defaultFornecedorShouldBeFound("nome.specified=true");

        // Get all the fornecedorList where nome is null
        defaultFornecedorShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorsByNomeContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome contains DEFAULT_NOME
        defaultFornecedorShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the fornecedorList where nome contains UPDATED_NOME
        defaultFornecedorShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFornecedorsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where nome does not contain DEFAULT_NOME
        defaultFornecedorShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the fornecedorList where nome does not contain UPDATED_NOME
        defaultFornecedorShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFornecedorsByIdentificadorIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where identificador equals to DEFAULT_IDENTIFICADOR
        defaultFornecedorShouldBeFound("identificador.equals=" + DEFAULT_IDENTIFICADOR);

        // Get all the fornecedorList where identificador equals to UPDATED_IDENTIFICADOR
        defaultFornecedorShouldNotBeFound("identificador.equals=" + UPDATED_IDENTIFICADOR);
    }

    @Test
    @Transactional
    void getAllFornecedorsByIdentificadorIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where identificador in DEFAULT_IDENTIFICADOR or UPDATED_IDENTIFICADOR
        defaultFornecedorShouldBeFound("identificador.in=" + DEFAULT_IDENTIFICADOR + "," + UPDATED_IDENTIFICADOR);

        // Get all the fornecedorList where identificador equals to UPDATED_IDENTIFICADOR
        defaultFornecedorShouldNotBeFound("identificador.in=" + UPDATED_IDENTIFICADOR);
    }

    @Test
    @Transactional
    void getAllFornecedorsByIdentificadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where identificador is not null
        defaultFornecedorShouldBeFound("identificador.specified=true");

        // Get all the fornecedorList where identificador is null
        defaultFornecedorShouldNotBeFound("identificador.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorsByIdentificadorContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where identificador contains DEFAULT_IDENTIFICADOR
        defaultFornecedorShouldBeFound("identificador.contains=" + DEFAULT_IDENTIFICADOR);

        // Get all the fornecedorList where identificador contains UPDATED_IDENTIFICADOR
        defaultFornecedorShouldNotBeFound("identificador.contains=" + UPDATED_IDENTIFICADOR);
    }

    @Test
    @Transactional
    void getAllFornecedorsByIdentificadorNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where identificador does not contain DEFAULT_IDENTIFICADOR
        defaultFornecedorShouldNotBeFound("identificador.doesNotContain=" + DEFAULT_IDENTIFICADOR);

        // Get all the fornecedorList where identificador does not contain UPDATED_IDENTIFICADOR
        defaultFornecedorShouldBeFound("identificador.doesNotContain=" + UPDATED_IDENTIFICADOR);
    }

    @Test
    @Transactional
    void getAllFornecedorsByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone equals to DEFAULT_TELEFONE
        defaultFornecedorShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the fornecedorList where telefone equals to UPDATED_TELEFONE
        defaultFornecedorShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllFornecedorsByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultFornecedorShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the fornecedorList where telefone equals to UPDATED_TELEFONE
        defaultFornecedorShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllFornecedorsByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone is not null
        defaultFornecedorShouldBeFound("telefone.specified=true");

        // Get all the fornecedorList where telefone is null
        defaultFornecedorShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorsByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone contains DEFAULT_TELEFONE
        defaultFornecedorShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the fornecedorList where telefone contains UPDATED_TELEFONE
        defaultFornecedorShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllFornecedorsByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where telefone does not contain DEFAULT_TELEFONE
        defaultFornecedorShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the fornecedorList where telefone does not contain UPDATED_TELEFONE
        defaultFornecedorShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllFornecedorsByDataCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where dataCadastro equals to DEFAULT_DATA_CADASTRO
        defaultFornecedorShouldBeFound("dataCadastro.equals=" + DEFAULT_DATA_CADASTRO);

        // Get all the fornecedorList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultFornecedorShouldNotBeFound("dataCadastro.equals=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllFornecedorsByDataCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where dataCadastro in DEFAULT_DATA_CADASTRO or UPDATED_DATA_CADASTRO
        defaultFornecedorShouldBeFound("dataCadastro.in=" + DEFAULT_DATA_CADASTRO + "," + UPDATED_DATA_CADASTRO);

        // Get all the fornecedorList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultFornecedorShouldNotBeFound("dataCadastro.in=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllFornecedorsByDataCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where dataCadastro is not null
        defaultFornecedorShouldBeFound("dataCadastro.specified=true");

        // Get all the fornecedorList where dataCadastro is null
        defaultFornecedorShouldNotBeFound("dataCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorsByProdutoIsEqualToSomething() throws Exception {
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            fornecedorRepository.saveAndFlush(fornecedor);
            produto = ProdutoResourceIT.createEntity(em);
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        em.persist(produto);
        em.flush();
        fornecedor.addProduto(produto);
        fornecedorRepository.saveAndFlush(fornecedor);
        Long produtoId = produto.getId();
        // Get all the fornecedorList where produto equals to produtoId
        defaultFornecedorShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the fornecedorList where produto equals to (produtoId + 1)
        defaultFornecedorShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }

    @Test
    @Transactional
    void getAllFornecedorsByEnderecoIsEqualToSomething() throws Exception {
        Endereco endereco;
        if (TestUtil.findAll(em, Endereco.class).isEmpty()) {
            fornecedorRepository.saveAndFlush(fornecedor);
            endereco = EnderecoResourceIT.createEntity(em);
        } else {
            endereco = TestUtil.findAll(em, Endereco.class).get(0);
        }
        em.persist(endereco);
        em.flush();
        fornecedor.setEndereco(endereco);
        endereco.setFornecedor(fornecedor);
        fornecedorRepository.saveAndFlush(fornecedor);
        Long enderecoId = endereco.getId();
        // Get all the fornecedorList where endereco equals to enderecoId
        defaultFornecedorShouldBeFound("enderecoId.equals=" + enderecoId);

        // Get all the fornecedorList where endereco equals to (enderecoId + 1)
        defaultFornecedorShouldNotBeFound("enderecoId.equals=" + (enderecoId + 1));
    }

    @Test
    @Transactional
    void getAllFornecedorsByEntradaFinanceiraIsEqualToSomething() throws Exception {
        EntradaFinanceira entradaFinanceira;
        if (TestUtil.findAll(em, EntradaFinanceira.class).isEmpty()) {
            fornecedorRepository.saveAndFlush(fornecedor);
            entradaFinanceira = EntradaFinanceiraResourceIT.createEntity(em);
        } else {
            entradaFinanceira = TestUtil.findAll(em, EntradaFinanceira.class).get(0);
        }
        em.persist(entradaFinanceira);
        em.flush();
        fornecedor.addEntradaFinanceira(entradaFinanceira);
        fornecedorRepository.saveAndFlush(fornecedor);
        Long entradaFinanceiraId = entradaFinanceira.getId();
        // Get all the fornecedorList where entradaFinanceira equals to entradaFinanceiraId
        defaultFornecedorShouldBeFound("entradaFinanceiraId.equals=" + entradaFinanceiraId);

        // Get all the fornecedorList where entradaFinanceira equals to (entradaFinanceiraId + 1)
        defaultFornecedorShouldNotBeFound("entradaFinanceiraId.equals=" + (entradaFinanceiraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFornecedorShouldBeFound(String filter) throws Exception {
        restFornecedorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].identificador").value(hasItem(DEFAULT_IDENTIFICADOR)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())));

        // Check, that the count call also returns 1
        restFornecedorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFornecedorShouldNotBeFound(String filter) throws Exception {
        restFornecedorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFornecedorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFornecedor() throws Exception {
        // Get the fornecedor
        restFornecedorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor
        Fornecedor updatedFornecedor = fornecedorRepository.findById(fornecedor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFornecedor are not directly saved in db
        em.detach(updatedFornecedor);
        updatedFornecedor
            .nome(UPDATED_NOME)
            .identificador(UPDATED_IDENTIFICADOR)
            .telefone(UPDATED_TELEFONE)
            .dataCadastro(UPDATED_DATA_CADASTRO);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(updatedFornecedor);

        restFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFornecedor.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testFornecedor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testFornecedor.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void putNonExistingFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFornecedorWithPatch() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor using partial update
        Fornecedor partialUpdatedFornecedor = new Fornecedor();
        partialUpdatedFornecedor.setId(fornecedor.getId());

        partialUpdatedFornecedor.nome(UPDATED_NOME);

        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFornecedor))
            )
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFornecedor.getIdentificador()).isEqualTo(DEFAULT_IDENTIFICADOR);
        assertThat(testFornecedor.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testFornecedor.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void fullUpdateFornecedorWithPatch() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor using partial update
        Fornecedor partialUpdatedFornecedor = new Fornecedor();
        partialUpdatedFornecedor.setId(fornecedor.getId());

        partialUpdatedFornecedor
            .nome(UPDATED_NOME)
            .identificador(UPDATED_IDENTIFICADOR)
            .telefone(UPDATED_TELEFONE)
            .dataCadastro(UPDATED_DATA_CADASTRO);

        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFornecedor))
            )
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFornecedor.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testFornecedor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testFornecedor.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void patchNonExistingFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fornecedorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeDelete = fornecedorRepository.findAll().size();

        // Delete the fornecedor
        restFornecedorMockMvc
            .perform(delete(ENTITY_API_URL_ID, fornecedor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
