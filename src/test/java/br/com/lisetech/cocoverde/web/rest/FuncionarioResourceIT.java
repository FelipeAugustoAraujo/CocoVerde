package br.com.lisetech.cocoverde.web.rest;

import static br.com.lisetech.cocoverde.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.Endereco;
import br.com.lisetech.cocoverde.domain.Funcionario;
import br.com.lisetech.cocoverde.repository.FuncionarioRepository;
import br.com.lisetech.cocoverde.service.dto.FuncionarioDTO;
import br.com.lisetech.cocoverde.service.mapper.FuncionarioMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link FuncionarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuncionarioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_NASCIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_NASCIMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICADOR = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICADOR = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_CADASTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CADASTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_VALOR_BASE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_BASE = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_BASE = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/funcionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioMapper funcionarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionarioMockMvc;

    private Funcionario funcionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .nome(DEFAULT_NOME)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .identificador(DEFAULT_IDENTIFICADOR)
            .telefone(DEFAULT_TELEFONE)
            .dataCadastro(DEFAULT_DATA_CADASTRO)
            .valorBase(DEFAULT_VALOR_BASE);
        return funcionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createUpdatedEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .identificador(UPDATED_IDENTIFICADOR)
            .telefone(UPDATED_TELEFONE)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .valorBase(UPDATED_VALOR_BASE);
        return funcionario;
    }

    @BeforeEach
    public void initTest() {
        funcionario = createEntity(em);
    }

    @Test
    @Transactional
    void createFuncionario() throws Exception {
        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();
        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);
        restFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate + 1);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFuncionario.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testFuncionario.getIdentificador()).isEqualTo(DEFAULT_IDENTIFICADOR);
        assertThat(testFuncionario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testFuncionario.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testFuncionario.getValorBase()).isEqualByComparingTo(DEFAULT_VALOR_BASE);
    }

    @Test
    @Transactional
    void createFuncionarioWithExistingId() throws Exception {
        // Create the Funcionario with an existing ID
        funcionario.setId(1L);
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuncionarios() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO)))
            .andExpect(jsonPath("$.[*].identificador").value(hasItem(DEFAULT_IDENTIFICADOR)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].valorBase").value(hasItem(sameNumber(DEFAULT_VALOR_BASE))));
    }

    @Test
    @Transactional
    void getFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get the funcionario
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, funcionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO))
            .andExpect(jsonPath("$.identificador").value(DEFAULT_IDENTIFICADOR))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
            .andExpect(jsonPath("$.valorBase").value(sameNumber(DEFAULT_VALOR_BASE)));
    }

    @Test
    @Transactional
    void getFuncionariosByIdFiltering() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        Long id = funcionario.getId();

        defaultFuncionarioShouldBeFound("id.equals=" + id);
        defaultFuncionarioShouldNotBeFound("id.notEquals=" + id);

        defaultFuncionarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFuncionarioShouldNotBeFound("id.greaterThan=" + id);

        defaultFuncionarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFuncionarioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFuncionariosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nome equals to DEFAULT_NOME
        defaultFuncionarioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the funcionarioList where nome equals to UPDATED_NOME
        defaultFuncionarioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultFuncionarioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the funcionarioList where nome equals to UPDATED_NOME
        defaultFuncionarioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nome is not null
        defaultFuncionarioShouldBeFound("nome.specified=true");

        // Get all the funcionarioList where nome is null
        defaultFuncionarioShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByNomeContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nome contains DEFAULT_NOME
        defaultFuncionarioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the funcionarioList where nome contains UPDATED_NOME
        defaultFuncionarioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nome does not contain DEFAULT_NOME
        defaultFuncionarioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the funcionarioList where nome does not contain UPDATED_NOME
        defaultFuncionarioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataNascimento equals to DEFAULT_DATA_NASCIMENTO
        defaultFuncionarioShouldBeFound("dataNascimento.equals=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the funcionarioList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultFuncionarioShouldNotBeFound("dataNascimento.equals=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataNascimento in DEFAULT_DATA_NASCIMENTO or UPDATED_DATA_NASCIMENTO
        defaultFuncionarioShouldBeFound("dataNascimento.in=" + DEFAULT_DATA_NASCIMENTO + "," + UPDATED_DATA_NASCIMENTO);

        // Get all the funcionarioList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultFuncionarioShouldNotBeFound("dataNascimento.in=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataNascimento is not null
        defaultFuncionarioShouldBeFound("dataNascimento.specified=true");

        // Get all the funcionarioList where dataNascimento is null
        defaultFuncionarioShouldNotBeFound("dataNascimento.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataNascimentoContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataNascimento contains DEFAULT_DATA_NASCIMENTO
        defaultFuncionarioShouldBeFound("dataNascimento.contains=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the funcionarioList where dataNascimento contains UPDATED_DATA_NASCIMENTO
        defaultFuncionarioShouldNotBeFound("dataNascimento.contains=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataNascimentoNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataNascimento does not contain DEFAULT_DATA_NASCIMENTO
        defaultFuncionarioShouldNotBeFound("dataNascimento.doesNotContain=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the funcionarioList where dataNascimento does not contain UPDATED_DATA_NASCIMENTO
        defaultFuncionarioShouldBeFound("dataNascimento.doesNotContain=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdentificadorIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where identificador equals to DEFAULT_IDENTIFICADOR
        defaultFuncionarioShouldBeFound("identificador.equals=" + DEFAULT_IDENTIFICADOR);

        // Get all the funcionarioList where identificador equals to UPDATED_IDENTIFICADOR
        defaultFuncionarioShouldNotBeFound("identificador.equals=" + UPDATED_IDENTIFICADOR);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdentificadorIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where identificador in DEFAULT_IDENTIFICADOR or UPDATED_IDENTIFICADOR
        defaultFuncionarioShouldBeFound("identificador.in=" + DEFAULT_IDENTIFICADOR + "," + UPDATED_IDENTIFICADOR);

        // Get all the funcionarioList where identificador equals to UPDATED_IDENTIFICADOR
        defaultFuncionarioShouldNotBeFound("identificador.in=" + UPDATED_IDENTIFICADOR);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdentificadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where identificador is not null
        defaultFuncionarioShouldBeFound("identificador.specified=true");

        // Get all the funcionarioList where identificador is null
        defaultFuncionarioShouldNotBeFound("identificador.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdentificadorContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where identificador contains DEFAULT_IDENTIFICADOR
        defaultFuncionarioShouldBeFound("identificador.contains=" + DEFAULT_IDENTIFICADOR);

        // Get all the funcionarioList where identificador contains UPDATED_IDENTIFICADOR
        defaultFuncionarioShouldNotBeFound("identificador.contains=" + UPDATED_IDENTIFICADOR);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdentificadorNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where identificador does not contain DEFAULT_IDENTIFICADOR
        defaultFuncionarioShouldNotBeFound("identificador.doesNotContain=" + DEFAULT_IDENTIFICADOR);

        // Get all the funcionarioList where identificador does not contain UPDATED_IDENTIFICADOR
        defaultFuncionarioShouldBeFound("identificador.doesNotContain=" + UPDATED_IDENTIFICADOR);
    }

    @Test
    @Transactional
    void getAllFuncionariosByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where telefone equals to DEFAULT_TELEFONE
        defaultFuncionarioShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the funcionarioList where telefone equals to UPDATED_TELEFONE
        defaultFuncionarioShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllFuncionariosByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultFuncionarioShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the funcionarioList where telefone equals to UPDATED_TELEFONE
        defaultFuncionarioShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllFuncionariosByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where telefone is not null
        defaultFuncionarioShouldBeFound("telefone.specified=true");

        // Get all the funcionarioList where telefone is null
        defaultFuncionarioShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where telefone contains DEFAULT_TELEFONE
        defaultFuncionarioShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the funcionarioList where telefone contains UPDATED_TELEFONE
        defaultFuncionarioShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllFuncionariosByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where telefone does not contain DEFAULT_TELEFONE
        defaultFuncionarioShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the funcionarioList where telefone does not contain UPDATED_TELEFONE
        defaultFuncionarioShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataCadastro equals to DEFAULT_DATA_CADASTRO
        defaultFuncionarioShouldBeFound("dataCadastro.equals=" + DEFAULT_DATA_CADASTRO);

        // Get all the funcionarioList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultFuncionarioShouldNotBeFound("dataCadastro.equals=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataCadastro in DEFAULT_DATA_CADASTRO or UPDATED_DATA_CADASTRO
        defaultFuncionarioShouldBeFound("dataCadastro.in=" + DEFAULT_DATA_CADASTRO + "," + UPDATED_DATA_CADASTRO);

        // Get all the funcionarioList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultFuncionarioShouldNotBeFound("dataCadastro.in=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataCadastro is not null
        defaultFuncionarioShouldBeFound("dataCadastro.specified=true");

        // Get all the funcionarioList where dataCadastro is null
        defaultFuncionarioShouldNotBeFound("dataCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByValorBaseIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where valorBase equals to DEFAULT_VALOR_BASE
        defaultFuncionarioShouldBeFound("valorBase.equals=" + DEFAULT_VALOR_BASE);

        // Get all the funcionarioList where valorBase equals to UPDATED_VALOR_BASE
        defaultFuncionarioShouldNotBeFound("valorBase.equals=" + UPDATED_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllFuncionariosByValorBaseIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where valorBase in DEFAULT_VALOR_BASE or UPDATED_VALOR_BASE
        defaultFuncionarioShouldBeFound("valorBase.in=" + DEFAULT_VALOR_BASE + "," + UPDATED_VALOR_BASE);

        // Get all the funcionarioList where valorBase equals to UPDATED_VALOR_BASE
        defaultFuncionarioShouldNotBeFound("valorBase.in=" + UPDATED_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllFuncionariosByValorBaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where valorBase is not null
        defaultFuncionarioShouldBeFound("valorBase.specified=true");

        // Get all the funcionarioList where valorBase is null
        defaultFuncionarioShouldNotBeFound("valorBase.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByValorBaseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where valorBase is greater than or equal to DEFAULT_VALOR_BASE
        defaultFuncionarioShouldBeFound("valorBase.greaterThanOrEqual=" + DEFAULT_VALOR_BASE);

        // Get all the funcionarioList where valorBase is greater than or equal to UPDATED_VALOR_BASE
        defaultFuncionarioShouldNotBeFound("valorBase.greaterThanOrEqual=" + UPDATED_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllFuncionariosByValorBaseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where valorBase is less than or equal to DEFAULT_VALOR_BASE
        defaultFuncionarioShouldBeFound("valorBase.lessThanOrEqual=" + DEFAULT_VALOR_BASE);

        // Get all the funcionarioList where valorBase is less than or equal to SMALLER_VALOR_BASE
        defaultFuncionarioShouldNotBeFound("valorBase.lessThanOrEqual=" + SMALLER_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllFuncionariosByValorBaseIsLessThanSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where valorBase is less than DEFAULT_VALOR_BASE
        defaultFuncionarioShouldNotBeFound("valorBase.lessThan=" + DEFAULT_VALOR_BASE);

        // Get all the funcionarioList where valorBase is less than UPDATED_VALOR_BASE
        defaultFuncionarioShouldBeFound("valorBase.lessThan=" + UPDATED_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllFuncionariosByValorBaseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where valorBase is greater than DEFAULT_VALOR_BASE
        defaultFuncionarioShouldNotBeFound("valorBase.greaterThan=" + DEFAULT_VALOR_BASE);

        // Get all the funcionarioList where valorBase is greater than SMALLER_VALOR_BASE
        defaultFuncionarioShouldBeFound("valorBase.greaterThan=" + SMALLER_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllFuncionariosByEnderecoIsEqualToSomething() throws Exception {
        Endereco endereco;
        if (TestUtil.findAll(em, Endereco.class).isEmpty()) {
            funcionarioRepository.saveAndFlush(funcionario);
            endereco = EnderecoResourceIT.createEntity(em);
        } else {
            endereco = TestUtil.findAll(em, Endereco.class).get(0);
        }
        em.persist(endereco);
        em.flush();
        funcionario.setEndereco(endereco);
        endereco.setFuncionario(funcionario);
        funcionarioRepository.saveAndFlush(funcionario);
        Long enderecoId = endereco.getId();
        // Get all the funcionarioList where endereco equals to enderecoId
        defaultFuncionarioShouldBeFound("enderecoId.equals=" + enderecoId);

        // Get all the funcionarioList where endereco equals to (enderecoId + 1)
        defaultFuncionarioShouldNotBeFound("enderecoId.equals=" + (enderecoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuncionarioShouldBeFound(String filter) throws Exception {
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO)))
            .andExpect(jsonPath("$.[*].identificador").value(hasItem(DEFAULT_IDENTIFICADOR)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].valorBase").value(hasItem(sameNumber(DEFAULT_VALOR_BASE))));

        // Check, that the count call also returns 1
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuncionarioShouldNotBeFound(String filter) throws Exception {
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFuncionario() throws Exception {
        // Get the funcionario
        restFuncionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario
        Funcionario updatedFuncionario = funcionarioRepository.findById(funcionario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFuncionario are not directly saved in db
        em.detach(updatedFuncionario);
        updatedFuncionario
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .identificador(UPDATED_IDENTIFICADOR)
            .telefone(UPDATED_TELEFONE)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .valorBase(UPDATED_VALOR_BASE);
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(updatedFuncionario);

        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFuncionario.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testFuncionario.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testFuncionario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testFuncionario.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testFuncionario.getValorBase()).isEqualByComparingTo(UPDATED_VALOR_BASE);
    }

    @Test
    @Transactional
    void putNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncionarioWithPatch() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario using partial update
        Funcionario partialUpdatedFuncionario = new Funcionario();
        partialUpdatedFuncionario.setId(funcionario.getId());

        partialUpdatedFuncionario
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .identificador(UPDATED_IDENTIFICADOR)
            .valorBase(UPDATED_VALOR_BASE);

        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFuncionario.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testFuncionario.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testFuncionario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testFuncionario.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testFuncionario.getValorBase()).isEqualByComparingTo(UPDATED_VALOR_BASE);
    }

    @Test
    @Transactional
    void fullUpdateFuncionarioWithPatch() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario using partial update
        Funcionario partialUpdatedFuncionario = new Funcionario();
        partialUpdatedFuncionario.setId(funcionario.getId());

        partialUpdatedFuncionario
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .identificador(UPDATED_IDENTIFICADOR)
            .telefone(UPDATED_TELEFONE)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .valorBase(UPDATED_VALOR_BASE);

        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFuncionario.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testFuncionario.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testFuncionario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testFuncionario.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testFuncionario.getValorBase()).isEqualByComparingTo(UPDATED_VALOR_BASE);
    }

    @Test
    @Transactional
    void patchNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeDelete = funcionarioRepository.findAll().size();

        // Delete the funcionario
        restFuncionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
