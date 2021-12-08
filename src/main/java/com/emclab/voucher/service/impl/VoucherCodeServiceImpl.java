package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.MyOrder;
import com.emclab.voucher.domain.MyUser;
import com.emclab.voucher.domain.OrderStatus;
import com.emclab.voucher.domain.Voucher;
import com.emclab.voucher.domain.VoucherCode;
import com.emclab.voucher.domain.VoucherStatus;
import com.emclab.voucher.repository.MyOrderRepository;
import com.emclab.voucher.repository.OrderStatusRepository;
import com.emclab.voucher.repository.VoucherCodeRepository;
import com.emclab.voucher.service.MyUserService;
import com.emclab.voucher.service.VoucherCodeService;
import com.emclab.voucher.service.dto.VoucherCodeDTO;
import com.emclab.voucher.service.mapper.MyUserMapper;
import com.emclab.voucher.service.mapper.VoucherCodeMapper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VoucherCode}.
 */
@Service
@Transactional
public class VoucherCodeServiceImpl implements VoucherCodeService {

    private final Logger log = LoggerFactory.getLogger(VoucherCodeServiceImpl.class);

    private final VoucherCodeRepository voucherCodeRepository;

    private final VoucherCodeMapper voucherCodeMapper;

    @Autowired
    MyUserService myUserService;

    @Autowired
    MyUserMapper myUserMapper;

    @Autowired
    MyOrderRepository myOrderRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    public VoucherCodeServiceImpl(VoucherCodeRepository voucherCodeRepository, VoucherCodeMapper voucherCodeMapper) {
        this.voucherCodeRepository = voucherCodeRepository;
        this.voucherCodeMapper = voucherCodeMapper;
    }

    @Override
    public VoucherCodeDTO save(VoucherCodeDTO voucherCodeDTO) {
        log.debug("Request to save VoucherCode : {}", voucherCodeDTO);
        VoucherCode voucherCode = voucherCodeMapper.toEntity(voucherCodeDTO);
        voucherCode = voucherCodeRepository.save(voucherCode);
        return voucherCodeMapper.toDto(voucherCode);
    }

    @Override
    public Optional<VoucherCodeDTO> partialUpdate(VoucherCodeDTO voucherCodeDTO) {
        log.debug("Request to partially update VoucherCode : {}", voucherCodeDTO);

        return voucherCodeRepository
            .findById(voucherCodeDTO.getId())
            .map(
                existingVoucherCode -> {
                    voucherCodeMapper.partialUpdate(existingVoucherCode, voucherCodeDTO);

                    return existingVoucherCode;
                }
            )
            .map(voucherCodeRepository::save)
            .map(voucherCodeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoucherCodeDTO> findAll() {
        log.debug("Request to get all VoucherCodes");
        return voucherCodeRepository.findAll().stream().map(voucherCodeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VoucherCodeDTO> findOne(Long id) {
        log.debug("Request to get VoucherCode : {}", id);
        return voucherCodeRepository.findById(id).map(voucherCodeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoucherCode : {}", id);
        voucherCodeRepository.deleteById(id);
    }

    @Override
    public Long countVoucherCodeByVoucher(Voucher voucher, VoucherStatus voucherStatus) {
        return voucherCodeRepository.countByVoucherAndStatus(voucher, voucherStatus);
    }

    @Override
    public List<VoucherCodeDTO> getVoucherCodeByOrderOfCurrentUser(Authentication authentication) {
        MyUser myUser = myUserMapper.toEntity(myUserService.getcurrentUser(authentication));

        String orderStatusname = "Đã thanh toán";
        OrderStatus orderStatus = orderStatusRepository.findByName(orderStatusname).get(0);
        List<MyOrder> myOrders = myOrderRepository.findByUserAndStatus(myUser, orderStatus);

        List<VoucherCodeDTO> resultVoucherCodeDTOs = new ArrayList<VoucherCodeDTO>();

        for (MyOrder myOrder : myOrders) {
            List<VoucherCode> voucherCodes = voucherCodeRepository.findByOrder(myOrder);

            for (VoucherCode voucherCode : voucherCodes) {
                resultVoucherCodeDTOs.add(voucherCodeMapper.toDto(voucherCode));
            }
        }

        return resultVoucherCodeDTOs;
    }
}
