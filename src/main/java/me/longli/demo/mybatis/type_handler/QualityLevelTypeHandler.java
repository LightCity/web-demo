package me.longli.demo.mybatis.type_handler;

import me.longli.demo.jpa.entity.sub.QualityLevelEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.TINYINT)
public class QualityLevelTypeHandler extends BaseTypeHandler<QualityLevelEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, QualityLevelEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter.getValue().byteValue());
    }

    @Override
    public QualityLevelEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int anInt = rs.getInt(columnName);
        return (anInt == 0 && rs.wasNull()) ? null : QualityLevelEnum.getByValue(anInt);
    }

    @Override
    public QualityLevelEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int anInt = rs.getInt(columnIndex);
        return (anInt == 0 && rs.wasNull()) ? null : QualityLevelEnum.getByValue(anInt);
    }

    @Override
    public QualityLevelEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int anInt = cs.getInt(columnIndex);
        return (anInt == 0 && cs.wasNull()) ? null : QualityLevelEnum.getByValue(anInt);
    }
}
