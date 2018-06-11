myApp.service('chatService', function($q, $timeout) {
	
	service.RECONNECT_TIMEOUT = 30000;
	var base_url = "http://localhost:8081/CollaborationRestService";
	service.SOCKET_URL = base_url + "/chat";
	var service = {}, listener = $q.defer(), socket = {client:null, stomp:null}, message_ids = [];
	service.CHAT_TOPIC = "/topic/message";
	service.CHAT_BROKER = "/app/chat";
	
	service.send = function(message) {
		
		var id = Math.floor(Math.random()*100000);
		socket.stomp.send(service.CHAT_BROKER, {priority:9}, JSON.stringify({message: message, id: id}));
		message_ids.push();
		
	};
	
	service.receive = function() {
		
	};
	
	var reconnect = function() {
		$timeout(function(){
			initialize()
		}, RECONNECT_TIMEOUT);
	};
	
	var getMessage = function(data) {
		
	};
	
	var startListener = function() {
		
	};
	
	var initialize = function() {
		
		socket.client = new SockJS(service.SOCKET_URL);
		socket.stomp = Stomp.over(socket.client);
		socket.stomp.connect({}, startListener);
		socket.stomp.onClose = reconnect;
		
	};
	
	initialize();
	
});