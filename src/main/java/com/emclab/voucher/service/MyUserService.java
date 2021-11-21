package com.emclab.voucher.service;

import com.emclab.voucher.domain.MyUser;
import com.emclab.voucher.service.dto.MyUserDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emclab.voucher.domain.MyUser}.
 */
public interface MyUserService {
    /**
     * Save a myUser.
     *
     * @param myUserDTO the entity to save.
     * @return the persisted entity.
     */
    MyUserDTO save(MyUserDTO myUserDTO);

    //    MyUser save(MyUser myUser);

    /**
     * Partially updates a myUser.
     *
     * @param myUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MyUserDTO> partialUpdate(MyUserDTO myUserDTO);

    /**
     * Get all the myUsers.
     *
     * @return the list of entities.
     */
    List<MyUserDTO> findAll();

    /**
     * Get the "id" myUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MyUserDTO> findOne(Long id);

    /**
     * Delete the "id" myUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
