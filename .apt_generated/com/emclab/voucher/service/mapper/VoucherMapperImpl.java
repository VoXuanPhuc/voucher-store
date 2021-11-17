package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.Product;
import com.emclab.voucher.domain.Voucher;
import com.emclab.voucher.service.dto.ProductDTO;
import com.emclab.voucher.service.dto.VoucherDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2021-11-10T10:27:43+0700", comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)")
@Component
public class VoucherMapperImpl implements VoucherMapper {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private ServiceTypeMapper serviceTypeMapper;
    @Autowired
    private VoucherStatusMapper voucherStatusMapper;

    @Override
    public List<Voucher> toEntity(List<VoucherDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Voucher> list = new ArrayList<Voucher>(dtoList.size());
        for (VoucherDTO voucherDTO : dtoList) {
            list.add(toEntity(voucherDTO));
        }

        return list;
    }

    @Override
    public List<VoucherDTO> toDto(List<Voucher> entityList) {
        if (entityList == null) {
            return null;
        }

        List<VoucherDTO> list = new ArrayList<VoucherDTO>(entityList.size());
        for (Voucher voucher : entityList) {
            list.add(toDto(voucher));
        }

        return list;
    }

    @Override
    public void partialUpdate(Voucher entity, VoucherDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getId() != null) {
            entity.id(dto.getId());
        }
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }
        if (dto.getQuantity() != null) {
            entity.setQuantity(dto.getQuantity());
        }
        if (dto.getStartTime() != null) {
            entity.setStartTime(dto.getStartTime());
        }
        if (dto.getExpriedTime() != null) {
            entity.setExpriedTime(dto.getExpriedTime());
        }
        if (entity.getProducts() != null) {
            Set<Product> set = productDTOSetToProductSet(dto.getProducts());
            if (set != null) {
                entity.getProducts().clear();
                entity.getProducts().addAll(set);
            }
        } else {
            Set<Product> set = productDTOSetToProductSet(dto.getProducts());
            if (set != null) {
                entity.setProducts(set);
            }
        }
        if (dto.getEvent() != null) {
            entity.setEvent(eventMapper.toEntity(dto.getEvent()));
        }
        if (dto.getType() != null) {
            entity.setType(serviceTypeMapper.toEntity(dto.getType()));
        }
    }

    @Override
    public VoucherDTO toDto(Voucher s) {
        if (s == null) {
            return null;
        }

        VoucherDTO voucherDTO = new VoucherDTO();

        voucherDTO.setProducts(productMapper.toDtoIdSet(s.getProducts()));
        voucherDTO.setEvent(eventMapper.toDtoId(s.getEvent()));
        voucherDTO.setType(serviceTypeMapper.toDtoId(s.getType()));
        voucherDTO.setId(s.getId());
        voucherDTO.setPrice(s.getPrice());
        voucherDTO.setQuantity(s.getQuantity());
        voucherDTO.setStartTime(s.getStartTime());
        voucherDTO.setExpriedTime(s.getExpriedTime());

        return voucherDTO;
    }

    @Override
    public VoucherDTO toDtoId(Voucher voucher) {
        if (voucher == null) {
            return null;
        }

        VoucherDTO voucherDTO = new VoucherDTO();

        voucherDTO.setId(voucher.getId());

        return voucherDTO;
    }

    @Override
    public Voucher toEntity(VoucherDTO voucherDTO) {
        if (voucherDTO == null) {
            return null;
        }

        Voucher voucher = new Voucher();

        voucher.id(voucherDTO.getId());
        voucher.setPrice(voucherDTO.getPrice());
        voucher.setQuantity(voucherDTO.getQuantity());
        voucher.setStartTime(voucherDTO.getStartTime());
        voucher.setExpriedTime(voucherDTO.getExpriedTime());
        voucher.setProducts(productDTOSetToProductSet(voucherDTO.getProducts()));
        voucher.setEvent(eventMapper.toEntity(voucherDTO.getEvent()));
        voucher.setType(serviceTypeMapper.toEntity(voucherDTO.getType()));

        return voucher;
    }

    protected Set<Product> productDTOSetToProductSet(Set<ProductDTO> set) {
        if (set == null) {
            return null;
        }

        Set<Product> set1 = new HashSet<Product>(Math.max((int) (set.size() / .75f) + 1, 16));
        for (ProductDTO productDTO : set) {
            set1.add(productMapper.toEntity(productDTO));
        }

        return set1;
    }
}
