package com.madgrid.web.util;




public class JSONOrder {
	
		
		private String value;
		private String input_address;
		private String input_transaction_hash;
		private String transaction_hash;

		public JSONOrder(String value, String input_address, String input_transaction_hash, String transaction_hash) 
		{
			this.value = value;
			this.input_address = input_address;
			this.input_transaction_hash = input_transaction_hash;
			this.transaction_hash = transaction_hash;

		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getInput_address() {
			return input_address;
		}

		public void setInput_address(String input_address) {
			this.input_address = input_address;
		}

		public String getInput_transaction_hash() {
			return input_transaction_hash;
		}

		public void setInput_transaction_hash(String input_transaction_hash) {
			this.input_transaction_hash = input_transaction_hash;
		}

		public String getTransaction_hash() {
			return transaction_hash;
		}

		public void setTransaction_hash(String transaction_hash) {
			this.transaction_hash = transaction_hash;
		}
		
		
		
}
