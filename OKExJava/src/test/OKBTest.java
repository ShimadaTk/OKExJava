package test;

import java.io.IOException;

import org.apache.http.HttpException;

import exchange.OKExData;

public class OKBTest {
	
	public static void main(String[] args) throws HttpException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		OKExData data = new OKExData();
		String result_BTC = data.getStockGet().depth("okb_btc&size=1");
		String result_ETH = data.getStockGet().depth("okb_eth&size=1");
		String result_IOTA = data.getStockGet().depth("iota_okb&size=1");
		
		String[] result_BTCSplit = result_BTC.split(",", 0);
		String[] result_ETHSplit = result_ETH.split(",", 0);
		String[] result_IOTASplit = result_IOTA.split(",", 0);
		
		for (int i = 0 ; i < result_BTCSplit.length ; i++){
			System.out.println(i + "番目の要素 = :" + result_BTCSplit[i]);
		}
		System.out.println(Double.parseDouble(result_BTCSplit[2].substring(9)));
		
		for (int i = 0 ; i < result_ETHSplit.length ; i++){
			System.out.println(i + "番目の要素 = :" + result_ETHSplit[i]);
		}
		System.out.println(Double.parseDouble(result_ETHSplit[2].substring(9)));
		
		for (int i = 0 ; i < result_IOTASplit.length ; i++){
			System.out.println(i + "番目の要素 = :" + result_IOTASplit[i]);
		}
		System.out.println(Double.parseDouble(result_IOTASplit[0].substring(10)));
		
		
		data.getOKB().exchange();
		for (java.util.Map.Entry<String, Double> entry2 : data.getOKBMap().entrySet()) {
		    System.out.println(entry2.getKey() + "：" + entry2.getValue());
		}
	}
	

}
