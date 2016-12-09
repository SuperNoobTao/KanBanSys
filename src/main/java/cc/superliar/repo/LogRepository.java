package cc.superliar.repo;



import cc.superliar.po.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Log's repository.
 * <pre>
 *     Extends the {@link Repository}
 *     Hide update and delete interface.
 * </pre>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
public interface LogRepository extends Repository<Log, Long> {

  Log save(Log log);

  List<Log> findAll();

  Page<Log> findAll(Pageable pageable);
}
