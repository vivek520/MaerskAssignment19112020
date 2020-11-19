package com.maersk.main;
import org.springframework.data.repository.CrudRepository;

import com.maersk.main.bean.ContainerDetails;
public interface IContainerRepository extends CrudRepository<ContainerDetails, String> {

	
}
