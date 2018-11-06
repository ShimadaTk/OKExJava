package exchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.lq.okex.rest.stock.IStockRestApi;
import com.lq.okex.rest.stock.impl.StockRestApi;

public class OKExData {
	
	private String url_prex = "https://www.okex.com";
	private IStockRestApi stockGet = new StockRestApi(url_prex);
	
	private OKExBTC BTC;
	private OKExETH ETH;
	private OKExOKB OKB;
	private OKExIOTA IOTA;
	
	/*
	 * ノードの数
	 */
	final private int nodeNum = 4;
	
	/*
	 * 各ノードへの重みとノード名を格納
	 */
	private Map<String, Double> BTCMap = new HashMap<>();
	private Map<String, Double> ETHMap = new HashMap<>();
	private Map<String, Double> OKBMap = new HashMap<>();
	private Map<String, Double> IOTAMap = new HashMap<>();
	
	/*
	 * 変換の際の状態を示す
	 */
	private OKExEx okexExchange = null;
	
	/*
	 * ノードの経路の重さ表すArrayList
	 */
	private Double gainWeight = 1.0;
	
	/*
	 * 決定経路
	 */
	private ArrayList<String> route = new ArrayList<>();
	
	public OKExData(){
		BTC = new OKExBTC(this);
		ETH = new OKExETH(this);
		OKB = new OKExOKB(this);
		IOTA = new OKExIOTA(this);
	}
	
	public String getUrl_prex(){
		return url_prex;
	}

	public IStockRestApi getStockGet(){
		return stockGet;
	}
	
	//ゲッター(BTC,ETH,OKB,IOTA)
	public OKExBTC getBTC(){
		return this.BTC;
	}	
	public OKExETH getETH(){
		return this.ETH;
	}	
	public OKExOKB getOKB(){
		return this.OKB;
	}
	public OKExIOTA getIOTA(){
		return this.IOTA;
	}
	
	//ゲッター(BTCMap,ETHMap,OKBMap,IOTAMap)
	public Map<String, Double> getBTCMap(){
		return BTCMap;		
	}	
	public Map<String, Double> getETHMap(){
		return ETHMap;		
	}
	public Map<String, Double> getOKBMap(){
		return OKBMap;		
	}
	public Map<String, Double> getIOTAMap(){
		return IOTAMap;		
	}
	
	//ゲッターnodeNum
	public int getNodeNum(){
		return nodeNum;		
	}	
	
	//ゲッター(扱う仮想通貨)
	public OKExEx getOKExExchange(){
		return okexExchange;
	}
	//セッター(扱う仮想通貨)
	public void setOKExExchange(OKExEx binanceExchange){
		this.okexExchange = binanceExchange;
	}	

	
	//ゲッター(結果の重み)
	public Double getGainWeight(){
		return gainWeight;
	}
	//セッター(結果の重み)
	public void setGainWeight(Double gainWeight){
		this.gainWeight *= gainWeight;
	}
	
	
	//ゲッター(経路)
	public ArrayList<String> getRoute(){
		return route;
	}
	//セッター(経路)
	public void setRoute(String nextNode){
		this.route.add(nextNode);
	}
}
