/**
 * Created by Think on 2017/7/4.
 */
import React, { Component } from 'react';
import {
    StyleSheet,
    Text,
    ScrollView,
    View,
    TouchableNativeFeedback,
    Dimensions,
    AsyncStorage
} from 'react-native';

import { NativeRouter, Route, Link, AndroidBackButton, Redirect } from 'react-router-native';
import { Avatar, Button, Divider, Card } from 'react-native-material-design';

import { NavigationActions } from 'react-navigation';


// import Storage from 'react-native-storage';

export default class MemoList extends Component {
    static navigationOptions = {
        title: 'Tiny Memo',
    };

    constructor(props) {
        super(props);

        this.state = {
            memo_list: new Array(12).fill({
                title: '备忘录',
                content: 'I don\'t know',
                time: new Date(),
            }),
        };
    }

    handleRedirect = (index) => {

        const navigateAction = NavigationActions.navigate({

            routeName: 'Memo',

            params: {
                memo: this.state.memo_list[index],
                index: index
            },

            action: NavigationActions.navigate({ routeName: 'memo'})
        });

        this.props.navigation.dispatch(navigateAction);
        //
        // this.props.navigation.navigate('Memo', {}, {
        //     memo: this.state.memo_list[index],
        //     index: index
        // })
    };

    handleSave = () => {
        // // 使用key来保存数据。这些数据一般是全局独有的，常常需要调用的。
        // // 除非你手动移除，这些数据会被永久保存，而且默认不会过期。
        // Storage.save({
        //     key: 'MEMO',  // 注意:请不要在key中使用_下划线符号!
        //     data: {
        //         memo: this.state.memo_list,
        //     },
        //
        //     // 如果不指定过期时间，则会使用defaultExpires参数
        //     // 如果设为null，则永不过期
        //     expires: null
        // });
    };

    componentDidMount() {

        const params = this.props.navigation.state.params;
        if(params && params.memo && params.index) {
            let { title, content, time } = this.props.navigation.state.params.memo;
            let index = this.props.navigation.state.params.index;
        }

        // // 读取
        // Storage.load({
        //     key: 'MEMO',
        //
        //     // syncInBackground(默认为true)意味着如果数据过期，
        //     // 在调用sync方法的同时先返回已经过期的数据。
        //     // 设置为false的话，则等待sync方法提供的最新数据(当然会需要更多时间)。
        //     syncInBackground: true,
        //
        //     // 你还可以给sync方法传递额外的参数
        //     syncParams: {
        //         extraFetchOptions: {
        //             // 各种参数
        //         },
        //         someFlag: true,
        //     },
        // }).then(ret => {
        //     // 如果找到数据，则在then方法中返回
        //     // 注意：这是异步返回的结果（不了解异步请自行搜索学习）
        //     // 你只能在then这个方法内继续处理ret数据
        //     // 而不能在then以外处理
        //     // 也没有办法“变成”同步返回
        //     // 你也可以使用“看似”同步的async/await语法
        //
        //     this.setState({ memo_list: ret.memo });
        // }).catch(err => {
        //     //如果没有找到数据且没有sync方法，
        //     //或者有其他异常，则在catch中返回
        //     console.warn(err.message);
        //     switch (err.name) {
        //         case 'NotFoundError':
        //             // TODO;
        //             break;
        //         case 'ExpiredError':
        //             // TODO
        //             break;
        //     }
        // })
    }

    render() {
        return (
            <View>
                <ScrollView>
                    <View style={styles.memo_list}>
                        {this.state.memo_list.map((item, index) => (
                            <Card key={index}>
                                <Card.Body>
                                    <TouchableNativeFeedback
                                        onPress={() => this.handleRedirect(index)}
                                        background={TouchableNativeFeedback.SelectableBackground()}>
                                        <View>
                                            <Text style={styles.memo_list__text}>{item.title}</Text>
                                            <Text style={styles.memo_list__time}>{item.time.toLocaleString()}</Text>
                                        </View>
                                    </TouchableNativeFeedback>
                                </Card.Body>
                            </Card>
                        ))}
                    </View>
                </ScrollView>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    memo_list: {
        // backgroundColor: '#F5FCFF',
    },
    memo_list__item: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'space-between'
    },
    memo_list__text: {
        color: 'black',
        fontSize: 16,
        overflow: 'hidden'
    },
    memo_list__time: {
        fontSize: 12
    },
});