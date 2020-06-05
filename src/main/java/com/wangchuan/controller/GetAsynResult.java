package com.wangchuan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController()
public class GetAsynResult {

	private int count;

	@GetMapping("/getCount")
	public int getCount() {
		return count;
	}

	@GetMapping("/test/getResult")
	public String getResult() {
		System.out.println("start to execute");
		async();
		System.out.println("end to execute");
		return "ok";
	}

	@GetMapping("/test/getAllResult")
	public String getAllResult() {
		List<Integer> ids = Arrays.asList(1,2,3,4);
		System.out.println("start " + Thread.currentThread().getName());
		CompletableFuture.supplyAsync(() -> batchProcess(ids))
				.thenAccept(result -> {
					System.out.println(result);
				    System.out.println(Thread.currentThread().getName());});
		return count+"";
	}

	private void async() {
		CompletableFuture.supplyAsync(() -> sleep(5))
				.thenAccept(result -> System.out.println("睡了"+ result+"秒"));
	}

	private int sleep(int time) {
		try{
			Thread.sleep(time * 1000);
			count++;
			System.out.println("sleep end " + time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return time * 10;
	}

	CompletableFuture<Integer> findAccount(int accountId, ExecutorService executorService) {
		return CompletableFuture.supplyAsync(() -> {
			// mock finding account from database
			sleep(accountId);
			System.out.println("business: " + Thread.currentThread().getName());
			return accountId * 10;
		}, executorService);
	}

	public List<Integer> batchProcess(List<Integer> accountIdList) {

		long startTime = System.currentTimeMillis();
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		System.out.println(Thread.currentThread().getName());
		// 并行根据accountId查找对应account
		List<CompletableFuture<Integer>> accountFindingFutureList = new ArrayList<>();
		for (Integer accountId : accountIdList) {
			CompletableFuture<Integer> account = findAccount(accountId, executorService);
			accountFindingFutureList.add(account);
		}

		// 使用allOf方法来表示所有的并行任务
		CompletableFuture<Void> allFutures =
				CompletableFuture
						.allOf(accountFindingFutureList.toArray(new CompletableFuture[0]));

		// 下面的方法可以帮助我们获得所有子任务的处理结果
		CompletableFuture<List<Integer>> finalResults = allFutures.thenApply(v -> {

			List<Integer> resu = new ArrayList<>();
			for (CompletableFuture<Integer> accountFindingFuture : accountFindingFutureList) {
				Integer join = accountFindingFuture.join();
				resu.add(join);
			}
			return resu;

		});
		List<Integer> join = finalResults.join();
		System.out.println("消耗时间：" + (System.currentTimeMillis() - startTime) / 1000);
		executorService.shutdown();
		return join;
	}
}
