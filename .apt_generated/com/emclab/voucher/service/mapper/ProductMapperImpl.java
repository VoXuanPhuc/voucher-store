package com.emclab.voucher.service.mapper;

import com.emclab.voucher.domain.Product;
import com.emclab.voucher.service.dto.ProductDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T10:27:44+0700",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210419-1022, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Product toEntity(ProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.id( dto.getId() );
        product.setCode( dto.getCode() );
        product.setImage( dto.getImage() );
        product.setCategory( categoryMapper.toEntity( dto.getCategory() ) );

        return product;
    }

    @Override
    public List<Product> toEntity(List<ProductDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( dtoList.size() );
        for ( ProductDTO productDTO : dtoList ) {
            list.add( toEntity( productDTO ) );
        }

        return list;
    }

    @Override
    public List<ProductDTO> toDto(List<Product> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( entityList.size() );
        for ( Product product : entityList ) {
            list.add( toDto( product ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Product entity, ProductDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getCode() != null ) {
            entity.setCode( dto.getCode() );
        }
        if ( dto.getImage() != null ) {
            entity.setImage( dto.getImage() );
        }
        if ( dto.getCategory() != null ) {
            entity.setCategory( categoryMapper.toEntity( dto.getCategory() ) );
        }
    }

    @Override
    public ProductDTO toDto(Product s) {
        if ( s == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setCategory( categoryMapper.toDtoId( s.getCategory() ) );
        productDTO.setId( s.getId() );
        productDTO.setCode( s.getCode() );
        productDTO.setImage( s.getImage() );

        return productDTO;
    }

    @Override
    public Set<ProductDTO> toDtoIdSet(Set<Product> product) {
        if ( product == null ) {
            return null;
        }

        Set<ProductDTO> set = new HashSet<ProductDTO>( Math.max( (int) ( product.size() / .75f ) + 1, 16 ) );
        for ( Product product1 : product ) {
            set.add( toDto( product1 ) );
        }

        return set;
    }
}
