package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*todo#3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        String sql = "select * from Users where UserID = ? and UserPassword = ?";

        log.debug("sql:{}", sql);

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, userId);
            statement.setString(2, userPassword);
//            log.error("findByUser setString after");
            ResultSet rs = statement.executeQuery();
//            log.error("findByUser rs setting setting");
            if (rs.next()) {
                User user = new User(
                        rs.getString("UserID"),
                        rs.getString("UserName"),
                        rs.getString("UserPassword"),
                        rs.getString("UserBirth"),
                        User.Auth.valueOf(rs.getString("UserAuth")),
                        rs.getInt("UserPoint"),
                        Objects.nonNull(rs.getTimestamp("CreatedAt")) ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("LatestLoginAt")) ? rs.getTimestamp("LatestLoginAt").toLocalDateTime() : null
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        String sql = "select * from Users where UserID = ?";

        log.debug("sql:{}", sql);

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, userId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("UserID"),
                        rs.getString("UserName"),
                        rs.getString("UserPassword"),
                        rs.getString("UserBirth"),
                        User.Auth.valueOf(rs.getString("UserAuth")),
                        rs.getInt("UserPoint"),
                        Objects.nonNull(rs.getTimestamp("CreatedAt")) ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("LatestLoginAt")) ? rs.getTimestamp("LatestLoginAt").toLocalDateTime() : null
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.
        String sql = "insert into Users(UserID, UserName, UserPassword, UserBirth, UserAuth, UserPoint, CreatedAt, LatestLoginAt) values(?,?,?,?,?,?,?,?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getUserPassword());
            statement.setString(4, user.getUserBirth());
            statement.setString(5, user.getUserAuth().toString());
            statement.setInt(6, user.getUserPoint());
            statement.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));
            statement.setTimestamp(8, user.getLatestLoginAt() != null ? Timestamp.valueOf(user.getLatestLoginAt()) : null);

            int result = statement.executeUpdate();
            log.debug("save-result:{}", result);
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.
        String sql = "delete from Users where UserID =?";
        log.debug("sql:{}", sql);

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, userId);
            int result = statement.executeUpdate();
            log.debug("result:{}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(User user) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        //todo#3-5 회원수정, executeUpdate()을 반환합니다.
        String sql = "update Users set UserID =?, UserName =?, UserPassword =?, UserBirth =?, UserAuth =?, UserPoint =? where UserID = \"" + user.getUserId() + "\"";
        try ( PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1,user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3,user.getUserPassword());
            statement.setString(4, user.getUserBirth());
            statement.setString(5, user.getUserAuth().toString());
            statement.setInt(6, user.getUserPoint());

            int result = statement.executeUpdate();
            log.debug("update-result:{}", result);
            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        //todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        String sql = "update Users set LatestLoginAt = ? where UserID = ?";
        log.debug("sql:{}", sql);

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,latestLoginAt.toString());
            statement.setString(2, userId);
            int result = statement.executeUpdate();
            log.debug("update:{}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "select count(*) as a from Users where UserID = ? group by UserID";
        log.debug("sql:{}", sql);

        Connection connection = DbConnectionThreadLocal.getConnection();

        int count = 0;
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,userId);
            log.debug("countByUserId");
            ResultSet result = statement.executeQuery();
            log.debug("result after");
            if (result.next()){
                count = result.getInt("a");
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        log.debug("return before");
        return count;
    }

}
