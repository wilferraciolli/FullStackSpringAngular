package com.linkedin.learning.config;

import converter.ReservationEntityToReservationResponseConverter;
import converter.ReservationRequestToReservationEntityConverter;
import converter.RoomEntityToReservableRoomResponseConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Conversion config. Register converters to allow to convert/map entities to and from http responses.
 */
@Configuration
public class ConversionConfig {

    private Set<Converter> getConverters (){
        Set<Converter> converters = new HashSet<>();
        converters.add(new RoomEntityToReservableRoomResponseConverter());
        converters.add(new ReservationRequestToReservationEntityConverter());
        converters.add(new ReservationEntityToReservationResponseConverter());

        return converters;
    }

    /**
     * Conversion service conversion service. This method is to implement the converters declared above.
     *
     * @return the conversion service
     */
    @Bean
    public ConversionService conversionService(){
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();

        return bean.getObject();
    }
}
