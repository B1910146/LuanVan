package warehouse.management.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.IntegrationTest;
import warehouse.management.app.domain.NhaCungCap;
import warehouse.management.app.repository.NhaCungCapRepository;
import warehouse.management.app.service.dto.NhaCungCapDTO;
import warehouse.management.app.service.mapper.NhaCungCapMapper;

/**
 * Integration tests for the {@link NhaCungCapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NhaCungCapResourceIT {

    private static final String DEFAULT_TEN_NCC = "AAAAAAAAAA";
    private static final String UPDATED_TEN_NCC = "BBBBBBBBBB";

    private static final String DEFAULT_SO_DT = "AAAAAAAAAA";
    private static final String UPDATED_SO_DT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DIA_CHI = "AAAAAAAAAA";
    private static final String UPDATED_DIA_CHI = "BBBBBBBBBB";

    private static final String DEFAULT_GHI_CHU = "AAAAAAAAAA";
    private static final String UPDATED_GHI_CHU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nha-cung-caps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NhaCungCapRepository nhaCungCapRepository;

    @Autowired
    private NhaCungCapMapper nhaCungCapMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNhaCungCapMockMvc;

    private NhaCungCap nhaCungCap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhaCungCap createEntity(EntityManager em) {
        NhaCungCap nhaCungCap = new NhaCungCap()
            .tenNCC(DEFAULT_TEN_NCC)
            .soDT(DEFAULT_SO_DT)
            .email(DEFAULT_EMAIL)
            .diaChi(DEFAULT_DIA_CHI)
            .ghiChu(DEFAULT_GHI_CHU);
        return nhaCungCap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhaCungCap createUpdatedEntity(EntityManager em) {
        NhaCungCap nhaCungCap = new NhaCungCap()
            .tenNCC(UPDATED_TEN_NCC)
            .soDT(UPDATED_SO_DT)
            .email(UPDATED_EMAIL)
            .diaChi(UPDATED_DIA_CHI)
            .ghiChu(UPDATED_GHI_CHU);
        return nhaCungCap;
    }

    @BeforeEach
    public void initTest() {
        nhaCungCap = createEntity(em);
    }

    @Test
    @Transactional
    void createNhaCungCap() throws Exception {
        int databaseSizeBeforeCreate = nhaCungCapRepository.findAll().size();
        // Create the NhaCungCap
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);
        restNhaCungCapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO)))
            .andExpect(status().isCreated());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeCreate + 1);
        NhaCungCap testNhaCungCap = nhaCungCapList.get(nhaCungCapList.size() - 1);
        assertThat(testNhaCungCap.getTenNCC()).isEqualTo(DEFAULT_TEN_NCC);
        assertThat(testNhaCungCap.getSoDT()).isEqualTo(DEFAULT_SO_DT);
        assertThat(testNhaCungCap.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNhaCungCap.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
        assertThat(testNhaCungCap.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    void createNhaCungCapWithExistingId() throws Exception {
        // Create the NhaCungCap with an existing ID
        nhaCungCap.setId(1L);
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        int databaseSizeBeforeCreate = nhaCungCapRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhaCungCapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenNCCIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhaCungCapRepository.findAll().size();
        // set the field null
        nhaCungCap.setTenNCC(null);

        // Create the NhaCungCap, which fails.
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        restNhaCungCapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO)))
            .andExpect(status().isBadRequest());

        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoDTIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhaCungCapRepository.findAll().size();
        // set the field null
        nhaCungCap.setSoDT(null);

        // Create the NhaCungCap, which fails.
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        restNhaCungCapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO)))
            .andExpect(status().isBadRequest());

        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhaCungCapRepository.findAll().size();
        // set the field null
        nhaCungCap.setEmail(null);

        // Create the NhaCungCap, which fails.
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        restNhaCungCapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO)))
            .andExpect(status().isBadRequest());

        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDiaChiIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhaCungCapRepository.findAll().size();
        // set the field null
        nhaCungCap.setDiaChi(null);

        // Create the NhaCungCap, which fails.
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        restNhaCungCapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO)))
            .andExpect(status().isBadRequest());

        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGhiChuIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhaCungCapRepository.findAll().size();
        // set the field null
        nhaCungCap.setGhiChu(null);

        // Create the NhaCungCap, which fails.
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        restNhaCungCapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO)))
            .andExpect(status().isBadRequest());

        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNhaCungCaps() throws Exception {
        // Initialize the database
        nhaCungCapRepository.saveAndFlush(nhaCungCap);

        // Get all the nhaCungCapList
        restNhaCungCapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhaCungCap.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenNCC").value(hasItem(DEFAULT_TEN_NCC)))
            .andExpect(jsonPath("$.[*].soDT").value(hasItem(DEFAULT_SO_DT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].diaChi").value(hasItem(DEFAULT_DIA_CHI)))
            .andExpect(jsonPath("$.[*].ghiChu").value(hasItem(DEFAULT_GHI_CHU)));
    }

    @Test
    @Transactional
    void getNhaCungCap() throws Exception {
        // Initialize the database
        nhaCungCapRepository.saveAndFlush(nhaCungCap);

        // Get the nhaCungCap
        restNhaCungCapMockMvc
            .perform(get(ENTITY_API_URL_ID, nhaCungCap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nhaCungCap.getId().intValue()))
            .andExpect(jsonPath("$.tenNCC").value(DEFAULT_TEN_NCC))
            .andExpect(jsonPath("$.soDT").value(DEFAULT_SO_DT))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.diaChi").value(DEFAULT_DIA_CHI))
            .andExpect(jsonPath("$.ghiChu").value(DEFAULT_GHI_CHU));
    }

    @Test
    @Transactional
    void getNonExistingNhaCungCap() throws Exception {
        // Get the nhaCungCap
        restNhaCungCapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNhaCungCap() throws Exception {
        // Initialize the database
        nhaCungCapRepository.saveAndFlush(nhaCungCap);

        int databaseSizeBeforeUpdate = nhaCungCapRepository.findAll().size();

        // Update the nhaCungCap
        NhaCungCap updatedNhaCungCap = nhaCungCapRepository.findById(nhaCungCap.getId()).get();
        // Disconnect from session so that the updates on updatedNhaCungCap are not directly saved in db
        em.detach(updatedNhaCungCap);
        updatedNhaCungCap.tenNCC(UPDATED_TEN_NCC).soDT(UPDATED_SO_DT).email(UPDATED_EMAIL).diaChi(UPDATED_DIA_CHI).ghiChu(UPDATED_GHI_CHU);
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(updatedNhaCungCap);

        restNhaCungCapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhaCungCapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO))
            )
            .andExpect(status().isOk());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeUpdate);
        NhaCungCap testNhaCungCap = nhaCungCapList.get(nhaCungCapList.size() - 1);
        assertThat(testNhaCungCap.getTenNCC()).isEqualTo(UPDATED_TEN_NCC);
        assertThat(testNhaCungCap.getSoDT()).isEqualTo(UPDATED_SO_DT);
        assertThat(testNhaCungCap.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNhaCungCap.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhaCungCap.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void putNonExistingNhaCungCap() throws Exception {
        int databaseSizeBeforeUpdate = nhaCungCapRepository.findAll().size();
        nhaCungCap.setId(count.incrementAndGet());

        // Create the NhaCungCap
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhaCungCapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhaCungCapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNhaCungCap() throws Exception {
        int databaseSizeBeforeUpdate = nhaCungCapRepository.findAll().size();
        nhaCungCap.setId(count.incrementAndGet());

        // Create the NhaCungCap
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhaCungCapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNhaCungCap() throws Exception {
        int databaseSizeBeforeUpdate = nhaCungCapRepository.findAll().size();
        nhaCungCap.setId(count.incrementAndGet());

        // Create the NhaCungCap
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhaCungCapMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNhaCungCapWithPatch() throws Exception {
        // Initialize the database
        nhaCungCapRepository.saveAndFlush(nhaCungCap);

        int databaseSizeBeforeUpdate = nhaCungCapRepository.findAll().size();

        // Update the nhaCungCap using partial update
        NhaCungCap partialUpdatedNhaCungCap = new NhaCungCap();
        partialUpdatedNhaCungCap.setId(nhaCungCap.getId());

        partialUpdatedNhaCungCap.tenNCC(UPDATED_TEN_NCC).email(UPDATED_EMAIL).diaChi(UPDATED_DIA_CHI).ghiChu(UPDATED_GHI_CHU);

        restNhaCungCapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhaCungCap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhaCungCap))
            )
            .andExpect(status().isOk());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeUpdate);
        NhaCungCap testNhaCungCap = nhaCungCapList.get(nhaCungCapList.size() - 1);
        assertThat(testNhaCungCap.getTenNCC()).isEqualTo(UPDATED_TEN_NCC);
        assertThat(testNhaCungCap.getSoDT()).isEqualTo(DEFAULT_SO_DT);
        assertThat(testNhaCungCap.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNhaCungCap.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhaCungCap.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void fullUpdateNhaCungCapWithPatch() throws Exception {
        // Initialize the database
        nhaCungCapRepository.saveAndFlush(nhaCungCap);

        int databaseSizeBeforeUpdate = nhaCungCapRepository.findAll().size();

        // Update the nhaCungCap using partial update
        NhaCungCap partialUpdatedNhaCungCap = new NhaCungCap();
        partialUpdatedNhaCungCap.setId(nhaCungCap.getId());

        partialUpdatedNhaCungCap
            .tenNCC(UPDATED_TEN_NCC)
            .soDT(UPDATED_SO_DT)
            .email(UPDATED_EMAIL)
            .diaChi(UPDATED_DIA_CHI)
            .ghiChu(UPDATED_GHI_CHU);

        restNhaCungCapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhaCungCap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhaCungCap))
            )
            .andExpect(status().isOk());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeUpdate);
        NhaCungCap testNhaCungCap = nhaCungCapList.get(nhaCungCapList.size() - 1);
        assertThat(testNhaCungCap.getTenNCC()).isEqualTo(UPDATED_TEN_NCC);
        assertThat(testNhaCungCap.getSoDT()).isEqualTo(UPDATED_SO_DT);
        assertThat(testNhaCungCap.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNhaCungCap.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhaCungCap.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void patchNonExistingNhaCungCap() throws Exception {
        int databaseSizeBeforeUpdate = nhaCungCapRepository.findAll().size();
        nhaCungCap.setId(count.incrementAndGet());

        // Create the NhaCungCap
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhaCungCapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nhaCungCapDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNhaCungCap() throws Exception {
        int databaseSizeBeforeUpdate = nhaCungCapRepository.findAll().size();
        nhaCungCap.setId(count.incrementAndGet());

        // Create the NhaCungCap
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhaCungCapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNhaCungCap() throws Exception {
        int databaseSizeBeforeUpdate = nhaCungCapRepository.findAll().size();
        nhaCungCap.setId(count.incrementAndGet());

        // Create the NhaCungCap
        NhaCungCapDTO nhaCungCapDTO = nhaCungCapMapper.toDto(nhaCungCap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhaCungCapMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nhaCungCapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhaCungCap in the database
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNhaCungCap() throws Exception {
        // Initialize the database
        nhaCungCapRepository.saveAndFlush(nhaCungCap);

        int databaseSizeBeforeDelete = nhaCungCapRepository.findAll().size();

        // Delete the nhaCungCap
        restNhaCungCapMockMvc
            .perform(delete(ENTITY_API_URL_ID, nhaCungCap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NhaCungCap> nhaCungCapList = nhaCungCapRepository.findAll();
        assertThat(nhaCungCapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
