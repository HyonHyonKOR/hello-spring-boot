package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplateMemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?,", memberRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List <Member> result = jdbcTemplate.query("select * from member where name= ?", memberRowMapper(),name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from ,member", memberRowMapper());
    }

    //쿼리 결과를 RowMapper로 맵핑해주는 함수
    //쿼리 결과 값(로우값)들을 RowMapper를 이용해 ResultSet을 자바 객체로 변환
    private RowMapper<Member> memberRowMapper(){
        //람다식으로 변경
        return (rs, rowNum) -> {
            //member 객체를 생성해 쿼리에서 가져온 결과(resultSet)를 맵핑
            Member member = new Member();
            member.setId(rs.getLong("id")); //resultSet의 column인 id인 값을 long으로 가져와 id에 대입
            member.setName(rs.getString("name")); // resultSet의 column이 name인 값을 String으로 가져와 name에 대입
            return member; //member 객체 반환
        };
    }
}
