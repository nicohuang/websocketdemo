var app = angular.module('App', []);
//主界面控制器

app.controller('SendController', function ($scope, $http)
{
    //获取列表数据
    $scope.sendMessage = function ()
    {
        $http(
            {
                method: 'POST',
                url: '/message/send',
                dataType: 'json',
                data: {
                    'username': $scope.username,
                    'message': $scope.message
                }
            }
        )
            .success
            (
                function (data)
                {
                    $scope.message = '';
                }
            )
            .error(function ()
            {

            });
    };

    $scope.select = function(user)
    {
        $scope.username = user;
    }
});

//app.directive("runoobDirective", function() {
//    return {
//        controller:[ "$scope", function ($scope) {
//            $scope.users = ['a','b'];
//            $scope.userList = ["555","bbb"];
//        }],
//        template : "<h1>{{users | json}}</h1>"
//    };
//});

//app.directive("runoobDirective", function ()
//{
//    var websocket = null;
//    if (window['WebSocket'])
//    // ws://host:port/project/websocketpath
//        websocket = new WebSocket("ws://" + window.location.host + '/websocket');
//    else
//        websocket = new new SockJS(PATH + '/websocket/socketjs');
//
//    websocket.onopen = function (event)
//    {
//        console.log('open', event);
//    };
//    websocket.onmessage = function (event)
//    {
//        console.log('message', event.data);
//        var sort = angular.fromJson(event.data.toString());
//        if (sort.type == 'message')
//        {
//            if (sort.username == sort.sender)
//            {
//                return {
//                    template: $('div[message] > ul').append('<li class="ul">'+sort.message + '</li>')
//                };
//            }
//            return {
//
//                template: $('div[message] > ul').append('<li>' + '['+sort.sender+']'+sort.message + '</li>')
//            };
//        }
//        else
//        {
//            return {
//                controller:[ "$scope", function ($scope) {
//                    $scope.users = ['a','b'];
//                    $scope.userList = ["555","bbb"];
//                }],
//                template : "<h1>{{users | json}}</h1>"
//            };
//
//        }
//    };
//});

app.directive("runoobDirective", function ()
{
    return {
        controller: ["$scope", function ($scope)
        {
            var websocket = null;
            if (window['WebSocket'])
            // ws://host:port/project/websocketpath
                websocket = new WebSocket("ws://" + window.location.host + '/websocket');
            else
                websocket = new new SockJS(PATH + '/websocket/socketjs');

            websocket.onopen = function (event)
            {
                console.log('open', event);
            };
            websocket.onmessage = function (event)
            {
                console.log('message', event.data);
                var sort = angular.fromJson(event.data.toString());
                if (sort.type == 'message')
                {
                    if (sort.username == sort.sender)
                    {
                        return {
                            template: $('div[message] > ul').append('<li class="ul">' + sort.message + '</li>')
                        };
                    }
                    return {

                        template: $('div[message] > ul').append('<li class="li">' + '[' + sort.sender + ']' + sort.message + '</li>')
                    };
                }
                else
                {
                    $scope.userList = sort.users;
                    $scope.$digest();
                }
            };
        }],
        template: "<h1>{{users | json}}</h1>"
    };
});


