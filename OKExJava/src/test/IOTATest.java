package test;

import java.io.IOException;

import org.apache.http.HttpException;

import exchange.OKExData;

public class IOTATest {
	
	public static void main(String[] args) throws HttpException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		OKExData data = new OKExData();
		String result_BTC = data.getStockGet().depth("iota_btc&size=1");
		String result_ETH = data.getStockGet().depth("iota_eth&size=1");
		String result_OKB = data.getStockGet().depth("iota_okb&size=1");
		
		String[] result_BTCSplit = result_BTC.split(",", 0);
		String[] result_ETHSplit = result_ETH.split(",", 0);
		String[] result_OKBSplit = result_OKB.split(",", 0);
		
		for (int i = 0 ; i < result_BTCSplit.length ; i++){
			System.out.println(i + "番目の要素 = :" + result_BTCSplit[i]);
		}
		System.out.println(Double.parseDouble(result_BTCSplit[2].substring(9)));
		
		for (int i = 0 ; i < result_ETHSplit.length ; i++){
			System.out.println(i + "番目の要素 = :" + result_ETHSplit[i]);
		}
		System.out.println(Double.parseDouble(result_ETHSplit[2].substring(9)));
		
		for (int i = 0 ; i < result_OKBSplit.length ; i++){
			System.out.println(i + "番目の要素 = :" + result_OKBSplit[i]);
		}
		System.out.println(Double.parseDouble(result_OKBSplit[2].substring(9)));
		
		
		data.getIOTA().exchange();
		for (java.util.Map.Entry<String, Double> entry2 : data.getIOTAMap().entrySet()) {
		    System.out.println(entry2.getKey() + "：" + entry2.getValue());
		}
	}

}
