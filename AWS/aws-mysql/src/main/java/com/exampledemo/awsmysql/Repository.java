package com.exampledemo.awsmysql;

import org.springframework.data.repository.CrudRepository;

public interface Repository extends CrudRepository<Entity,String> {
}
