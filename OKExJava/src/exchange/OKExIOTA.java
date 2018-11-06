package exchange;

import java.io.IOException;

import org.apache.http.HttpException;

public class OKExIOTA implements OKExEx{
	
	private OKExData data;	
	
	public OKExIOTA(OKExData data){
		this.data = data;
	}

	@Override
	public void exchange() throws HttpException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		/*
		 * 板情報はStringとしてリストに格納されているため、Double型にキャストする
		 */
		try{
		String result_BTC = data.getStockGet().depth("iota_btc&size=1");
		String result_ETH = data.getStockGet().depth("iota_eth&size=1");
		String result_OKB = data.getStockGet().depth("iota_okb&size=1");
		
		String[] result_BTCSplit = result_BTC.split(",", 0);
		String[] result_ETHSplit = result_ETH.split(",", 0);
		String[] result_OKBSplit = result_OKB.split(",", 0);
	
		Double value = Double.parseDouble(result_BTCSplit[2].substring(9));
		data.getIOTAMap().put("BTC", value*0.9985);
		value = Double.parseDouble(result_ETHSplit[2].substring(9));
		data.getIOTAMap().put("ETH", value*0.9985);
		value = Double.parseDouble(result_OKBSplit[2].substring(9));
		data.getIOTAMap().put("OKB",value*0.9985);
		}catch(Exception e){
			e.printStackTrace();
			data.getIOTAMap().put("BTC", 0.0);
			data.getIOTAMap().put("ETH", 0.0);
			data.getIOTAMap().put("IOTA", 0.0);
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
		
		
		//IOTA→BTC
		nextNode2 = "BTC";
		for(int i = 0; i < (data.getNodeNum()-2); i++){
			totalWeight2 = 1.0;//初期化
			nextWeight2 = 1.0;//初期化
			/*
			 * 計算開始
			 */
			totalWeight2 *= data.getIOTAMap().get("BTC");
			nextWeight2 *= data.getIOTAMap().get("BTC");
			
			//IOTA→BTC→OKB→ETH
			if(i==0){
				totalWeight2 *= data.getBTCMap().get("OKB");
				totalWeight2 *= data.getOKBMap().get("ETH");
				System.out.println(totalWeight2 + "IOTA;BTC;OKB;ETH");
				nextNode = nextNode2;
				nextWeight = nextWeight2;
				totalWeight = totalWeight2;
			}

			//IOTA→BTC→ETH
			else if(i==1){
				totalWeight2 *= data.getBTCMap().get("ETH");
				System.out.println(totalWeight2 + "IOTA;BTC;ETH");
			}
			if(i>=1 && totalWeight2 > totalWeight){
				totalWeight = totalWeight2;
			}		
		}
		
		//IOTA→OKB
		nextNode2 = "OKB";
		for(int i = 0; i < (data.getNodeNum()-2); i++){
			totalWeight2 = 1.0;//初期化
			nextWeight2 = 1.0;//初期化
			/*
			 * 計算開始
			 */
			totalWeight2 *= data.getIOTAMap().get("OKB");
			nextWeight2 *= data.getIOTAMap().get("OKB");
			
			//IOTA→OKB→BTC→ETH
			if(i==0){
				totalWeight2 *= data.getOKBMap().get("BTC");
				totalWeight2 *= data.getBTCMap().get("ETH");
				System.out.println(totalWeight2 + "IOTA;OKB;BTC;ETH;");
			}
			
			//IOTA→OKB→ETH
			else if(i==1){
				totalWeight2 *= data.getOKBMap().get("ETH");
				System.out.println(totalWeight2 + "IOTA;OKB;ETH");
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
		
		//IOTA→BTC
		if((data.getRoute().get(1)).equals("BTC")){
			for(int i = 0; i < (data.getNodeNum()-2); i++){
				totalWeight2 = 1.0;
				nextWeight2 = 1.0;
				if(i == 0){   ////IOTA→BTC→OKB→ETH
					nextNode2 = "OKB";
					nextWeight2 *= data.getBTCMap().get("OKB");
					totalWeight2 *= data.getBTCMap().get("OKB");
					totalWeight2 *= data.getOKBMap().get("ETH");
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}
				else if(i == 1){   ////IOTA→BTC→ETH
					nextNode2 = "ETH";
					nextWeight2 *= data.getBTCMap().get("ETH");
					totalWeight2 *= data.getBTCMap().get("ETH");
				}
				
				if(i>=1 && totalWeight2 > totalWeight){
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}	
			}
		}
		//IOTA→OKB
		else if((data.getRoute().get(1)).equals("OKB")){
			for(int i = 0; i < (data.getNodeNum()-2); i++){
				totalWeight2 = 1.0;
				nextWeight2 = 1.0;
				//IOTA→OKB→BTC→ETH
				if(i==0){
					nextNode2 = "BTC";
					nextWeight2 *= data.getOKBMap().get("BTC");
					totalWeight2 *= data.getOKBMap().get("BTC");
					totalWeight2 *= data.getBTCMap().get("ETH");
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}
			
				//IOTA→OKB→ETH
				else if(i==1){
					nextNode2 = "ETH";
					nextWeight2 *= data.getOKBMap().get("ETH");
					totalWeight2 *= data.getOKBMap().get("ETH");
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
		String nextNode = null;//決定経路
		Double nextWeight = 1.0;
		
		//IOTA→OKB→BTC→ETH
		if((data.getRoute().get(2)).equals("BTC")){
			nextNode = "ETH";
			nextWeight = data.getBTCMap().get("ETH");
		}
		//IOTA→BTC→OKB→ETH
		else if((data.getRoute().get(2)).equals("OKB")){
			nextNode = "ETH";
			nextWeight = data.getOKBMap().get("ETH");
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}

}
