package com.madgrid.web.util.quartz;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ojb.broker.query.Criteria;

import com.madgrid.dao.BlockcypherDAO;
import com.madgrid.model.Blockcypher;
import com.madgrid.web.util.Utils;
 
public class DeletePaymentForwardTask 
{
	public void deletePaymentForward() {
		BlockcypherDAO blockcypherDAO = new BlockcypherDAO();
		try{
			Criteria criteria = new Criteria();
			List<Blockcypher> blockcypherList = blockcypherDAO.getBlockcypherListByCriteria(criteria, null);
			for( Blockcypher blockcypher:blockcypherList){
				if( blockcypher.getExpiration().compareTo( Utils.today()) == -1){
					if( !Utils.nullOrBlank(blockcypher.getBlockcypherId())){
						deleteBlockCypherEndpoint(blockcypher.getBlockcypherId());
					}
					blockcypherDAO.deleteBlockcypher(blockcypher);
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void deleteBlockCypherEndpoint(String id) throws Exception {
		 
		String url = "https://api.blockcypher.com/v1/btc/main/payments/"+id;
		HttpClient client = new DefaultHttpClient();
		HttpDelete delete = new HttpDelete(url);
 
		delete.addHeader("content-type", "application/x-www-form-urlencoded");
		HttpResponse response = client.execute(delete);
 
				
	}
}