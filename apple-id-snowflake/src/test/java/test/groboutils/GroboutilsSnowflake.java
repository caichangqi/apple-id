package test.groboutils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import com.appleframework.id.SnowflakeIdGenerator;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

public class GroboutilsSnowflake {

	/**
	 * ���̲߳�������
	 * 
	 * @author lihzh(One Coder)
	 * @date 2012-6-12 ����9:18:11
	 * @blog http://www.coderli.com
	 */
	@Test
	public void MultiRequestsTest() {
		
		final Map<Long, Long> idMaps = new ConcurrentHashMap<Long, Long>();
		final SnowflakeIdGenerator idGenerator = SnowflakeIdGenerator.getInstance();
		
		// ����һ��Runner
		TestRunnable runner = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				// ��������
				for (int i = 0; i < 1000; i++) {
					Long id = idGenerator.generateIdMini();
					System.out.println("id: " + id);
	                if (idMaps.containsKey(id)) {
	                    System.out.println("Error: " + id);
	                }
	                idMaps.put(id, id);
				}
			}
		};
		int runnerCount = 1000;
		// Rnner���飬�뵱�ڲ������ٸ���
		TestRunnable[] trs = new TestRunnable[runnerCount];
		for (int i = 0; i < runnerCount; i++) {
			trs[i] = runner;
		}
		// ����ִ�ж��̲߳���������Runner����ǰ�涨��ĵ���Runner��ɵ����鴫��
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
		try {
			// ��������ִ�������ﶨ�������
			mttr.runTestRunnables();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
