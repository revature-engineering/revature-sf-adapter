package com.revature.sfadapter.data.services;

import com.revature.sfadapter.util.SFWSAccessObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by August Duet on 11/21/2016.
 */

@Transactional
@Service
public class SFWSAccessTokenDaoService extends DaoService<SFWSAccessObject, String>{

}
