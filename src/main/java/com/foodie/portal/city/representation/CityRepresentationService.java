package com.foodie.portal.city.representation;

import com.foodie.portal.commons.Pagination;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Maps.newHashMap;

@Service
public class CityRepresentationService {

    public static final String SELECT_SQL = "SELECT * FROM FOODIE_CITY LIMIT :limit OFFSET :offset;";
    public static final String COUNT_SQL = "SELECT COUNT(1) FROM FOODIE_CITY;";

    @Autowired
    private  NamedParameterJdbcTemplate jdbcTemplate;

    public Pagination<CitySummaryRepresentation> listCities(int pageIndex, int pageSize) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("limit", pageSize);
        parameters.addValue("offset", (pageIndex - 1) * pageSize);

        List<CitySummaryRepresentation> cities = jdbcTemplate.query(SELECT_SQL, parameters, new BeanPropertyRowMapper(CitySummaryRepresentation.class));

        int total = jdbcTemplate.queryForObject(COUNT_SQL, newHashMap(), Integer.class);
        return Pagination.of(total, pageIndex, pageSize,  cities);
    }


    public List<CitySummaryRepresentation> listCitiesByIds(Set<String> ids) {
        String select_sql = "SELECT * FROM FOODIE_CITY where id in (:ids)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids.stream().collect(Collectors.joining(",")));

        return jdbcTemplate.query(select_sql, parameters, new BeanPropertyRowMapper(CitySummaryRepresentation.class));

    }
}
