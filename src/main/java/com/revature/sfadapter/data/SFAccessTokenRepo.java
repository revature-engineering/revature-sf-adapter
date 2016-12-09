package com.revature.sfadapter.data;

import com.revature.sfadapter.util.SFWSAccessObject;
import org.springframework.stereotype.Repository;

/**
 * Created by August Duet on 11/21/2016.
 */

@Repository
public interface SFAccessTokenRepo extends BaseRepository<SFWSAccessObject, String> {
}
