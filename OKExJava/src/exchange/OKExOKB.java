package exchange;

import java.io.IOException;

import org.apache.http.HttpException;

public class OKExOKB implements OKExEx{
	
	private OKExData data;	
	
	public OKExOKB(OKExData data){
		this.data = data;
	}

	@Override
	public void exchange() throws HttpException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		/*
		 * 板情報はStringとしてリストに格納されているため、Double型にキャストする
		 */
		try{
		String result_BTC = data.getStockGet().depth("okb_btc&size=1");
		String result_ETH = data.getStockGet().depth("okb_eth&size=1");
		String result_IOTA = data.getStockGet().depth("iota_okb&size=1");
		
		String[] result_BTCSplit = result_BTC.split(",", 0);
		String[] result_ETHSplit = result_ETH.split(",", 0);
		String[] result_IOTASplit = result_IOTA.split(",", 0);
	
		Double value = Double.parseDouble(result_BTCSplit[2].substring(9));
		data.getOKBMap().put("BTC", value*0.9985);
		value = Double.parseDouble(result_ETHSplit[2].substring(9));
		data.getOKBMap().put("ETH", value*0.9985);
		value = Double.parseDouble(result_IOTASplit[0].substring(10));
		data.getOKBMap().put("IOTA", (1/value)*0.9985);
		}catch(Exception e){
			e.printStackTrace();
			data.getOKBMap().put("BTC", 0.0);
			data.getOKBMap().put("ETH", 0.0);
			data.getOKBMap().put("IOTA", 0.0);
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
