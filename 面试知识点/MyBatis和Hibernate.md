# MyBatis和Hibernate

[TOC]

### 1.Hibernate是全自动，而MyBatis是半自动 

Hibernate完全可以通过对象关系模型实现对数据库的操作，拥有完整的JavaBean对象与数据库的映射结构来自动生成SQL语句。而MyBatis仅有基本的字段映射，对象数据以及对象实际关系仍然需要通过定制SQL语句来实现和管理。

### 2.Hibernate数据库移植性远大于MyBatis。

Hibernate通过它强大的映射结构和hql语言，大大降低了对象与数据库（[Oracle](https://link.jianshu.com?t=http://lib.csdn.net/base/oracle)、[MySQL](https://link.jianshu.com?t=http://lib.csdn.net/base/mysql)等）的耦合性，而MyBatis由于需要手写sql，因此与数据库的耦合性直接取决于程序员写SQL的方法，如果SQL不具通用性而用了很多某数据库特性的sql语句的话，移植性也会随之降低很多，成本很高。

### 3.Hibernate拥有完整的日志系统，MyBatis则欠缺一些。

Hibernate日志系统非常健全，涉及广泛，包括：SQL记录、关系异常、优化警告、缓存提示、脏数据警告等；而MyBatis则除了基本记录功能外，功能薄弱很多。

### 4.MyBatis相比Hibernate需要关心很多细节

Hibernate配置要比MyBatis复杂的多，学习成本也比MyBatis高。但也正因为MyBatis使用简单，才导致它要比Hibernate关心很多技术细节。MyBatis由于不用考虑很多细节，开发模式上与传统jdbc区别很小，因此很容易上手并开发项目，但忽略细节会导致项目前期bug较多，因而开发出相对稳定的软件很慢，而开发出软件却很快。Hibernate则正好与之相反。但是如果使用Hibernate很熟练的话，实际上开发效率丝毫不差于甚至超越MyBatis。

### 5.SQL直接优化上，MyBatis要比Hibernate方便很多

由于MyBatis的sql都是写在xml里，因此优化sql比Hibernate方便很多。而Hibernate的sql很多都是自动生成的，无法直接维护sql；虽有hql，但功能还是不及sql强大，见到报表等变态需求时，hql也歇菜，也就是说hql是有局限的；hibernate虽然也支持原生sql，但开发模式上却与orm不同，需要转换思维，因此使用上不是非常方便。总之写sql的灵活度上Hibernate不及MyBatis。

