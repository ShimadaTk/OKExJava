package exchange;

import java.io.IOException;

import org.apache.http.HttpException;


public class OKExBTC implements OKExEx{
	
	private OKExData data;	
	
	public OKExBTC(OKExData data){
		this.data = data;
	}

	@Override
	public void exchange() throws HttpException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		/*
		 * 板情報はStringとしてリストに格納されているため、Double型にキャストする
		 */
		try{
		String result_ETH = data.getStockGet().depth("eth_btc&size=1");
		String result_OKB = data.getStockGet().depth("okb_btc&size=1");
		String result_IOTA = data.getStockGet().depth("iota_btc&size=1");
		
		String[] result_ETHSplit = result_ETH.split(",", 0);
		String[] result_OKBSplit = result_OKB.split(",", 0);
		String[] result_IOTASplit = result_IOTA.split(",", 0);
	
		Double value = Double.parseDouble(result_ETHSplit[0].substring(10));
		data.getBTCMap().put("ETH", (1/value)*0.9985);
		value = Double.parseDouble(result_OKBSplit[0].substring(10));
		data.getBTCMap().put("OKB", (1/value)*0.9985);
		value = Double.parseDouble(result_IOTASplit[0].substring(10));
		data.getBTCMap().put("IOTA", (1/value)*0.9985);
		}catch(Exception e){
			e.printStackTrace();
			data.getBTCMap().put("ETH", 0.0);
			data.getBTCMap().put("OKB", 0.0);
			data.getBTCMap().put("IOTA", 0.0);
		}		
	}

	@Override
	public void transition() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("ETHかIOTAのどちらかしか選択できません。");
	}

	@Override
	public void transition2() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("ETHかIOTAのどちらかしか選択できません。");
	}

	@Override
	public void transition3() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("ETHかIOTAのどちらかしか選択できません。");
	}
	

}
