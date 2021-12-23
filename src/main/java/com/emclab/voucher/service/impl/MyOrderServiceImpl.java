package com.emclab.voucher.service.impl;

import com.emclab.voucher.domain.MyOrder;
import com.emclab.voucher.domain.VoucherCode;
import com.emclab.voucher.repository.MyOrderRepository;
import com.emclab.voucher.service.MyOrderService;
import com.emclab.voucher.service.dto.MyOrderDTO;
import com.emclab.voucher.service.dto.VoucherCodeDTO;
import com.emclab.voucher.service.mapper.MyOrderMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MyOrder}.
 */
@Service
@Transactional
public class MyOrderServiceImpl implements MyOrderService {

    private final Logger log = LoggerFactory.getLogger(MyOrderServiceImpl.class);

    private final MyOrderRepository myOrderRepository;

    private final MyOrderMapper myOrderMapper;

    public MyOrderServiceImpl(MyOrderRepository myOrderRepository, MyOrderMapper myOrderMapper) {
        this.myOrderRepository = myOrderRepository;
        this.myOrderMapper = myOrderMapper;
    }

    @Override
    public MyOrderDTO save(MyOrderDTO myOrderDTO) {
        log.debug("Request to save MyOrder : {}", myOrderDTO);

        //change voucher codes status
        //        for(VoucherCodeDTO voucherCode : myOrderDTO.g)

        MyOrder myOrder = myOrderMapper.toEntity(myOrderDTO);

        myOrder = myOrderRepository.save(myOrder);
        return myOrderMapper.toDto(myOrder);
    }

    @Override
    public Optional<MyOrderDTO> partialUpdate(MyOrderDTO myOrderDTO) {
        log.debug("Request to partially update MyOrder : {}", myOrderDTO);

        return myOrderRepository
            .findById(myOrderDTO.getId())
            .map(
                existingMyOrder -> {
                    myOrderMapper.partialUpdate(existingMyOrder, myOrderDTO);

                    return existingMyOrder;
                }
            )
            .map(myOrderRepository::save)
            .map(myOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyOrderDTO> findAll() {
        log.debug("Request to get all MyOrders");
        return myOrderRepository.findAll().stream().map(myOrderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MyOrderDTO> findOne(Long id) {
        log.debug("Request to get MyOrder : {}", id);
        return myOrderRepository.findById(id).map(myOrderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyOrder : {}", id);
        myOrderRepository.deleteById(id);
    }
}
