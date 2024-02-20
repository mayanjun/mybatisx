# MybatisX

A very easy-to-use database middleware. Please see homepage of [mybatisx](http://mayanjun.org/projects/mybatisx) for more details.

## 1.0
- init version

## 1.0.2
- fix some bugs

## 1.0.3
- Change the field type of idGeneratorType in DataSourceConfig to Class<? IdGenerator>
- Add config field snowflakeIndexes to config the indexes of the default IdGenerator

## 1.0.4
- Added entity scan to ensure generate the entity Mapper.

## 1.0.5
- Added multi tenant data isolation

## 1.0.6
- Multi tenant support
- Multi tenant transaction bug fixed
- Default sharding provider

## 1.0.7
- Solve the problem that multi-field sorting cannot be sorted in fields order.
- DataType optimized.
- Fixed some bugs.