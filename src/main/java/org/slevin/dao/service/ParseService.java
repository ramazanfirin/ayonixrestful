package org.slevin.dao.service;


import org.slevin.common.AyonixPerson;
import org.slevin.common.ParseResult;
import org.slevin.dao.AyonixPersonDao;
import org.slevin.dao.ParseDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class ParseService extends EntityService<ParseResult> implements ParseDao {

	
}

