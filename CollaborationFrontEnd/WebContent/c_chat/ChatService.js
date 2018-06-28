/*myApp.filter('reverse', function() {
	  return function(items) {
	    return items.slice().reverse();
	  };
	});

	myApp.directive('ngFocus', function() {
	  return function(scope, element, attrs) {
	    element.bind('click', function() {
	      $('.' + attrs.ngFocus)[0].focus();
	    });
	  };
	});

	myApp.factory('socket', function($rootScope) {
	  alert('app factory')
	    var socket = new SockJS('http://localhost:8081/CollaborationRestService/chat');
	    var stompClient = Stomp.over(socket);
	    stompClient.connect('', '', function(frame) {
	      $rootScope.$broadcast('sockConnected', frame);
	    });

	    return {
	      stompClient: stompClient
	    };
	});*/

myApp.service('chatService', ['$http', '$q', '$timeout', function($http, $q, $timeout) {
	
	console.log('Starting Service');
	
	var base_url = 'http://localhost:8081/CollaborationRestService';

	var service = {}, listener = $q.defer(), socket = {client:null, stomp:null}, message_ids = [];
	
	service.RECONNECT_TIMEOUT = 30000;
	service.SOCKET_URL = base_url + '/chat';
	
	service.CHAT_TOPIC = '/topic/message';
	service.CHAT_BROKER = '/app/chat';
	
	service.send = function(message) {
		
		var id = Math.floor(Math.random()*100000);
		socket.stomp.send(service.CHAT_BROKER, {priority:9}, JSON.stringify({message: message, id: id}));
		message_ids.push(id);
		
	};
	
	service.receive = function() {
		return listener.promise;
	};
	
	var reconnect = function() {
		$timeout(function() {
			initialize()
		}, service.RECONNECT_TIMEOUT);
	};
	
	var getMessage = function(data) {
		var message = JSON.parse(data);
		var out = {};
		out.message = message.message;
		out.time = message.time;
		return out;
	};
	
	
	var startListener = function() {
		socket.stomp.subscribe(service.CHAT_TOPIC, function(data) {
			listener.notify(getMessage(data.body));
		});
	};
	
	var initialize = function() {
		
		socket.client = new SockJS(service.SOCKET_URL);
		socket.stomp = Stomp.over(socket.client);
		socket.stomp.connect({}, startListener);
		socket.stomp.onclose = reconnect;
		
	};
	
	initialize();
	
	return service;
	
}]);