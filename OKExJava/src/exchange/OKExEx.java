package exchange;

import java.io.IOException;

import org.apache.http.HttpException;

public interface OKExEx {
	
	
	/**
	 * 各ノード間の重みを計算、格納
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public void exchange() throws HttpException, IOException;

	/*
	 * 効率の良い経路を決定する
	 */
	//第2ノードを決定する
	public void transition();

	//第3ノードを決定する
	public void transition2();

	//第4ノードを決定する
	public void transition3();

}
