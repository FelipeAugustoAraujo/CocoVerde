package br.com.lisetech.cocoverde.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lisetech.cocoverde.IntegrationTest;
import br.com.lisetech.cocoverde.domain.EntradaFinanceira;
import br.com.lisetech.cocoverde.domain.Imagem;
import br.com.lisetech.cocoverde.domain.SaidaFinanceira;
import br.com.lisetech.cocoverde.repository.ImagemRepository;
import br.com.lisetech.cocoverde.service.dto.ImagemDTO;
import br.com.lisetech.cocoverde.service.mapper.ImagemMapper;
import jakarta.persistence.EntityManager;
import java.util.Base64;
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
 * Integration tests for the {@link ImagemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ImagemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/imagems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ImagemRepository imagemRepository;

    @Autowired
    private ImagemMapper imagemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImagemMockMvc;

    private Imagem imagem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imagem createEntity(EntityManager em) {
        Imagem imagem = new Imagem()
            .name(DEFAULT_NAME)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .contentType(DEFAULT_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return imagem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imagem createUpdatedEntity(EntityManager em) {
        Imagem imagem = new Imagem()
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .contentType(UPDATED_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        return imagem;
    }

    @BeforeEach
    public void initTest() {
        imagem = createEntity(em);
    }

    @Test
    @Transactional
    void createImagem() throws Exception {
        int databaseSizeBeforeCreate = imagemRepository.findAll().size();
        // Create the Imagem
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);
        restImagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagemDTO)))
            .andExpect(status().isCreated());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeCreate + 1);
        Imagem testImagem = imagemList.get(imagemList.size() - 1);
        assertThat(testImagem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testImagem.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testImagem.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testImagem.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testImagem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createImagemWithExistingId() throws Exception {
        // Create the Imagem with an existing ID
        imagem.setId(1L);
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);

        int databaseSizeBeforeCreate = imagemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllImagems() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList
        restImagemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imagem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getImagem() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get the imagem
        restImagemMockMvc
            .perform(get(ENTITY_API_URL_ID, imagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imagem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64.getEncoder().encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getImagemsByIdFiltering() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        Long id = imagem.getId();

        defaultImagemShouldBeFound("id.equals=" + id);
        defaultImagemShouldNotBeFound("id.notEquals=" + id);

        defaultImagemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultImagemShouldNotBeFound("id.greaterThan=" + id);

        defaultImagemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultImagemShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllImagemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where name equals to DEFAULT_NAME
        defaultImagemShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the imagemList where name equals to UPDATED_NAME
        defaultImagemShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllImagemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where name in DEFAULT_NAME or UPDATED_NAME
        defaultImagemShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the imagemList where name equals to UPDATED_NAME
        defaultImagemShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllImagemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where name is not null
        defaultImagemShouldBeFound("name.specified=true");

        // Get all the imagemList where name is null
        defaultImagemShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllImagemsByNameContainsSomething() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where name contains DEFAULT_NAME
        defaultImagemShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the imagemList where name contains UPDATED_NAME
        defaultImagemShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllImagemsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where name does not contain DEFAULT_NAME
        defaultImagemShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the imagemList where name does not contain UPDATED_NAME
        defaultImagemShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllImagemsByContentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where contentType equals to DEFAULT_CONTENT_TYPE
        defaultImagemShouldBeFound("contentType.equals=" + DEFAULT_CONTENT_TYPE);

        // Get all the imagemList where contentType equals to UPDATED_CONTENT_TYPE
        defaultImagemShouldNotBeFound("contentType.equals=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllImagemsByContentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where contentType in DEFAULT_CONTENT_TYPE or UPDATED_CONTENT_TYPE
        defaultImagemShouldBeFound("contentType.in=" + DEFAULT_CONTENT_TYPE + "," + UPDATED_CONTENT_TYPE);

        // Get all the imagemList where contentType equals to UPDATED_CONTENT_TYPE
        defaultImagemShouldNotBeFound("contentType.in=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllImagemsByContentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where contentType is not null
        defaultImagemShouldBeFound("contentType.specified=true");

        // Get all the imagemList where contentType is null
        defaultImagemShouldNotBeFound("contentType.specified=false");
    }

    @Test
    @Transactional
    void getAllImagemsByContentTypeContainsSomething() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where contentType contains DEFAULT_CONTENT_TYPE
        defaultImagemShouldBeFound("contentType.contains=" + DEFAULT_CONTENT_TYPE);

        // Get all the imagemList where contentType contains UPDATED_CONTENT_TYPE
        defaultImagemShouldNotBeFound("contentType.contains=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllImagemsByContentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where contentType does not contain DEFAULT_CONTENT_TYPE
        defaultImagemShouldNotBeFound("contentType.doesNotContain=" + DEFAULT_CONTENT_TYPE);

        // Get all the imagemList where contentType does not contain UPDATED_CONTENT_TYPE
        defaultImagemShouldBeFound("contentType.doesNotContain=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllImagemsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where description equals to DEFAULT_DESCRIPTION
        defaultImagemShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the imagemList where description equals to UPDATED_DESCRIPTION
        defaultImagemShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllImagemsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultImagemShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the imagemList where description equals to UPDATED_DESCRIPTION
        defaultImagemShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllImagemsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where description is not null
        defaultImagemShouldBeFound("description.specified=true");

        // Get all the imagemList where description is null
        defaultImagemShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllImagemsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where description contains DEFAULT_DESCRIPTION
        defaultImagemShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the imagemList where description contains UPDATED_DESCRIPTION
        defaultImagemShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllImagemsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList where description does not contain DEFAULT_DESCRIPTION
        defaultImagemShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the imagemList where description does not contain UPDATED_DESCRIPTION
        defaultImagemShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllImagemsBySaidaFinanceiraIsEqualToSomething() throws Exception {
        SaidaFinanceira saidaFinanceira;
        if (TestUtil.findAll(em, SaidaFinanceira.class).isEmpty()) {
            imagemRepository.saveAndFlush(imagem);
            saidaFinanceira = SaidaFinanceiraResourceIT.createEntity(em);
        } else {
            saidaFinanceira = TestUtil.findAll(em, SaidaFinanceira.class).get(0);
        }
        em.persist(saidaFinanceira);
        em.flush();
        imagem.setSaidaFinanceira(saidaFinanceira);
        imagemRepository.saveAndFlush(imagem);
        Long saidaFinanceiraId = saidaFinanceira.getId();
        // Get all the imagemList where saidaFinanceira equals to saidaFinanceiraId
        defaultImagemShouldBeFound("saidaFinanceiraId.equals=" + saidaFinanceiraId);

        // Get all the imagemList where saidaFinanceira equals to (saidaFinanceiraId + 1)
        defaultImagemShouldNotBeFound("saidaFinanceiraId.equals=" + (saidaFinanceiraId + 1));
    }

    @Test
    @Transactional
    void getAllImagemsByEntradaFinanceiraIsEqualToSomething() throws Exception {
        EntradaFinanceira entradaFinanceira;
        if (TestUtil.findAll(em, EntradaFinanceira.class).isEmpty()) {
            imagemRepository.saveAndFlush(imagem);
            entradaFinanceira = EntradaFinanceiraResourceIT.createEntity(em);
        } else {
            entradaFinanceira = TestUtil.findAll(em, EntradaFinanceira.class).get(0);
        }
        em.persist(entradaFinanceira);
        em.flush();
        imagem.setEntradaFinanceira(entradaFinanceira);
        imagemRepository.saveAndFlush(imagem);
        Long entradaFinanceiraId = entradaFinanceira.getId();
        // Get all the imagemList where entradaFinanceira equals to entradaFinanceiraId
        defaultImagemShouldBeFound("entradaFinanceiraId.equals=" + entradaFinanceiraId);

        // Get all the imagemList where entradaFinanceira equals to (entradaFinanceiraId + 1)
        defaultImagemShouldNotBeFound("entradaFinanceiraId.equals=" + (entradaFinanceiraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImagemShouldBeFound(String filter) throws Exception {
        restImagemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imagem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restImagemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImagemShouldNotBeFound(String filter) throws Exception {
        restImagemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImagemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingImagem() throws Exception {
        // Get the imagem
        restImagemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImagem() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();

        // Update the imagem
        Imagem updatedImagem = imagemRepository.findById(imagem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedImagem are not directly saved in db
        em.detach(updatedImagem);
        updatedImagem
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .contentType(UPDATED_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        ImagemDTO imagemDTO = imagemMapper.toDto(updatedImagem);

        restImagemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imagemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagemDTO))
            )
            .andExpect(status().isOk());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
        Imagem testImagem = imagemList.get(imagemList.size() - 1);
        assertThat(testImagem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImagem.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testImagem.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testImagem.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testImagem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingImagem() throws Exception {
        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();
        imagem.setId(longCount.incrementAndGet());

        // Create the Imagem
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imagemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImagem() throws Exception {
        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();
        imagem.setId(longCount.incrementAndGet());

        // Create the Imagem
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImagem() throws Exception {
        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();
        imagem.setId(longCount.incrementAndGet());

        // Create the Imagem
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImagemWithPatch() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();

        // Update the imagem using partial update
        Imagem partialUpdatedImagem = new Imagem();
        partialUpdatedImagem.setId(imagem.getId());

        partialUpdatedImagem
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .contentType(UPDATED_CONTENT_TYPE);

        restImagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImagem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImagem))
            )
            .andExpect(status().isOk());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
        Imagem testImagem = imagemList.get(imagemList.size() - 1);
        assertThat(testImagem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImagem.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testImagem.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testImagem.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testImagem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateImagemWithPatch() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();

        // Update the imagem using partial update
        Imagem partialUpdatedImagem = new Imagem();
        partialUpdatedImagem.setId(imagem.getId());

        partialUpdatedImagem
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .contentType(UPDATED_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);

        restImagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImagem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImagem))
            )
            .andExpect(status().isOk());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
        Imagem testImagem = imagemList.get(imagemList.size() - 1);
        assertThat(testImagem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImagem.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testImagem.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testImagem.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testImagem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingImagem() throws Exception {
        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();
        imagem.setId(longCount.incrementAndGet());

        // Create the Imagem
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, imagemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imagemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImagem() throws Exception {
        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();
        imagem.setId(longCount.incrementAndGet());

        // Create the Imagem
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imagemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImagem() throws Exception {
        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();
        imagem.setId(longCount.incrementAndGet());

        // Create the Imagem
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(imagemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImagem() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        int databaseSizeBeforeDelete = imagemRepository.findAll().size();

        // Delete the imagem
        restImagemMockMvc
            .perform(delete(ENTITY_API_URL_ID, imagem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
