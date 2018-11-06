package exchange;

import java.io.IOException;

import org.apache.http.HttpException;

public class OKExETH implements OKExEx{
	
	private OKExData data;	
	
	public OKExETH(OKExData data){
		this.data = data;
	}

	@Override
	public void exchange() throws HttpException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		/*
		 * 板情報はStringとしてリストに格納されているため、Double型にキャストする
		 */
		try{
		String result_BTC = data.getStockGet().depth("eth_btc&size=1");
		String result_OKB = data.getStockGet().depth("okb_eth&size=1");
		String result_IOTA = data.getStockGet().depth("iota_eth&size=1");
		
		String[] result_BTCSplit = result_BTC.split(",", 0);
		String[] result_OKBSplit = result_OKB.split(",", 0);
		String[] result_IOTASplit = result_IOTA.split(",", 0);
	
		Double value = Double.parseDouble(result_BTCSplit[2].substring(9));
		data.getETHMap().put("BTC", value*0.9985);
		value = Double.parseDouble(result_OKBSplit[0].substring(10));
		data.getETHMap().put("OKB", (1/value)*0.9985);
		value = Double.parseDouble(result_IOTASplit[0].substring(10));
		data.getETHMap().put("IOTA", (1/value)*0.9985);
		}catch(Exception e){
			e.printStackTrace();
			data.getETHMap().put("BTC", 0.0);
			data.getETHMap().put("OKB", 0.0);
			data.getETHMap().put("IOTA", 0.0);
		}
		
	}

	@Override
	public void transition() {
		String nextNode = null;//決定経路
		Double nextWeight = 1.0;
		Double totalWeight = 1.0;
		String nextNode2 = null;//仮経路
		Double totalWeight2 = 1.0;
		Double nextWeight2 = 1.0;
		
		
		//ETH→BTC
		nextNode2 = "BTC";
		for(int i = 0; i < (data.getNodeNum()-2); i++){
			totalWeight2 = 1.0;//初期化
			nextWeight2 = 1.0;//初期化
			/*
			 * 計算開始
			 */
			totalWeight2 *= data.getETHMap().get("BTC");
			nextWeight2 *= data.getETHMap().get("BTC");
			
			//ETH→BTC→OKB→IOTA
			if(i==0){
				totalWeight2 *= data.getBTCMap().get("OKB");
				totalWeight2 *= data.getOKBMap().get("IOTA");
				System.out.println(totalWeight2 + "ETH;BTC;OKB;IOTA");
				nextNode = nextNode2;
				nextWeight = nextWeight2;
				totalWeight = totalWeight2;
			}

			//ETH→BTC→IOTA
			else if(i==1){
				totalWeight2 *= data.getBTCMap().get("IOTA");
				System.out.println(totalWeight2 + "ETH;BTC;IOTA");
			}
			if(i>=1 && totalWeight2 > totalWeight){
				totalWeight = totalWeight2;
			}		
		}
		
		//ETH→OKB
		nextNode2 = "OKB";
		for(int i = 0; i < (data.getNodeNum()-2); i++){
			totalWeight2 = 1.0;//初期化
			nextWeight2 = 1.0;//初期化
			/*
			 * 計算開始
			 */
			totalWeight2 *= data.getETHMap().get("OKB");
			nextWeight2 *= data.getETHMap().get("OKB");
			
			//ETH→OKB→BTC→IOTA
			if(i==0){
				totalWeight2 *= data.getOKBMap().get("BTC");
				totalWeight2 *= data.getBTCMap().get("IOTA");
				System.out.println(totalWeight2 + "ETH;OKB;BTC;IOTA;");
			}
			
			//ETH→OKB→IOTA
			else if(i==1){
				totalWeight2 *= data.getOKBMap().get("IOTA");
				System.out.println(totalWeight2 + "ETH;OKB;IOTA");
			}
			
			if(totalWeight2 > totalWeight){
				nextNode = nextNode2;
				nextWeight = nextWeight2;
				totalWeight = totalWeight2;
			}	
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}

	@Override
	public void transition2() {
		String nextNode = null;//決定経路
		Double nextWeight = 1.0;
		Double totalWeight = 1.0;
		String nextNode2 = null;//仮経路
		Double totalWeight2 = 1.0;
		Double nextWeight2 = 1.0;
		
		//ETH→BTC
		if((data.getRoute().get(1)).equals("BTC")){
			for(int i = 0; i < (data.getNodeNum()-2); i++){
				totalWeight2 = 1.0;
				nextWeight2 = 1.0;
				if(i == 0){   ////ETH→BTC→OKB→IOTA
					nextNode2 = "BNB";
					nextWeight2 *= data.getBTCMap().get("OKB");
					totalWeight2 *= data.getBTCMap().get("OKB");
					totalWeight2 *= data.getOKBMap().get("IOTA");
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}
				else if(i == 1){   ////ETH→BTC→IOTA
					nextNode2 = "IOTA";
					nextWeight2 *= data.getBTCMap().get("IOTA");
					totalWeight2 *= data.getBTCMap().get("IOTA");
				}
				
				if(i>=1 && totalWeight2 > totalWeight){
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}	
			}
		}
		//ETH→OKB
		else if((data.getRoute().get(1)).equals("OKB")){
			for(int i = 0; i < (data.getNodeNum()-2); i++){
				totalWeight2 = 1.0;
				nextWeight2 = 1.0;
				//ETH→OKB→BTC→IOTA
				if(i==0){
					nextNode2 = "BTC";
					nextWeight2 *= data.getOKBMap().get("BTC");
					totalWeight2 *= data.getOKBMap().get("BTC");
					totalWeight2 *= data.getBTCMap().get("IOTA");
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}
			
				//ETH→OKB→IOTA
				else if(i==1){
					nextNode2 = "IOTA";
					nextWeight2 *= data.getOKBMap().get("IOTA");
					totalWeight2 *= data.getOKBMap().get("IOTA");
				}
			
				if(i>=1 && totalWeight2 > totalWeight){
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}
			}
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}

	@Override
	public void transition3() {
		// TODO 自動生成されたメソッド・スタブ
		String nextNode = null;//決定経路
		Double nextWeight = 1.0;
		
		//ETH→BTC→OKB→IOTA
		if((data.getRoute().get(2)).equals("BTC")){
			nextNode = "IOTA";
			nextWeight = data.getBTCMap().get("IOTA");
		}
		else if((data.getRoute().get(2)).equals("OKB")){
			nextNode = "IOTA";
			nextWeight = data.getOKBMap().get("IOTA");
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}
	
}
