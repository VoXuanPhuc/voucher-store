package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.MyUser;
import com.emclab.voucher.domain.Role;
import com.emclab.voucher.repository.MyUserRepository;
import com.emclab.voucher.repository.RoleRepository;
import com.emclab.voucher.service.MyUserService;
import com.emclab.voucher.service.dto.MyUserDTO;
import com.emclab.voucher.service.mapper.MyUserMapper;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MyUser}.
 */
@Service
@Transactional
public class MyUserServiceImpl implements MyUserService {

    private final Logger log = LoggerFactory.getLogger(MyUserServiceImpl.class);

    private final MyUserRepository myUserRepository;

    private final MyUserMapper myUserMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public MyUserServiceImpl(MyUserRepository myUserRepository, MyUserMapper myUserMapper) {
        this.myUserRepository = myUserRepository;
        this.myUserMapper = myUserMapper;
    }

    //    @Override
    //    public MyUser save(MyUser myUser) {
    //        MyUser newuser = new MyUser();
    //        //    	HashSet<Role> roles = new HashSet<Role> ();
    //
    //        Role role = new Role();
    //        role.setId((long) 2);
    //        role.setName("ROLE_USER");
    //        role.setCode("user");
    //        //    	Role role1 = new Role();
    //        //    	role.setId((long)2);
    //        //    	role.setName("ROLE_ADMIN");
    //        //    	role.setCode("admin");
    //
    //        //    	roles.add(role);
    //        //    	roles.add(role1);
    //        log.debug("Request to save MyUser : {}", myUser);
    //
    //        newuser.setUsername(myUser.getUsername());
    //        newuser.setEmail(myUser.getEmail());
    //        newuser.setPassword(myUser.getPassword());
    //        newuser.setPhone(myUser.getPhone());
    //        newuser.setFirstName("");
    //        newuser.setLastName("");
    //        newuser.setGender("");
    //
    //        //    	newuser.getRoles().add(role);
    //
    //        //    	role.getUsers().add(newuser);
    //
    //        MyUser user = myUserRepository.save(newuser);
    //        return (user);
    //    }

    @Override
    public MyUserDTO save(MyUserDTO myUserDTO) {
        Role role = new Role();
        role.setId((long) 2);
        role.setName("ROLE_USER");
        role.setCode("user");

        log.debug("Request to save MyUser : {}", myUserDTO);
        MyUser myUser = myUserMapper.toEntity(myUserDTO);
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));

        myUser.getRoles().add(role);

        myUser = myUserRepository.save(myUser);

        return myUserMapper.toDto(myUser);
    }

    @Override
    public Optional<MyUserDTO> partialUpdate(MyUserDTO myUserDTO) {
        log.debug("Request to partially update MyUser : {}", myUserDTO);

        return myUserRepository
            .findById(myUserDTO.getId())
            .map(
                existingMyUser -> {
                    myUserMapper.partialUpdate(existingMyUser, myUserDTO);

                    return existingMyUser;
                }
            )
            .map(myUserRepository::save)
            .map(myUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyUserDTO> findAll() {
        log.debug("Request to get all MyUsers");
        return myUserRepository.findAll().stream().map(myUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MyUserDTO> findOne(Long id) {
        log.debug("Request to get MyUser : {}", id);
        return myUserRepository.findById(id).map(myUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyUser : {}", id);
        myUserRepository.deleteById(id);
    }
}
