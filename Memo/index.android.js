/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
    AppRegistry,
    StyleSheet,
    ScrollView,
    Text,
    View
} from 'react-native';

import {
    StackNavigator,
} from 'react-navigation';
import NavigationBar from 'react-native-navigation-bar';
import { NativeRouter, Route, Link, AndroidBackButton, Redirect } from 'react-router-native';


import MemoList from './MemoList';
import MemoEdit from './MemoEdit';

const App = StackNavigator({
    Index: {
        screen: MemoList,
        path: 'index'
    },
    Memo: {
        screen: MemoEdit,
        path: 'memo'
    }
});

export default class Memo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            memo_list: new Array(12).fill({
                title: 'what?',
                content: 'I don\'t know',
                time: new Date()
            }),

            current_memo: null,
            current_index: -1,

            can_go_back: false,
            can_add: true,

            redirect: null
        }
    }

    handleOpenMemo = (index, cb = function(){}) => {
        this.setState({
            current_memo: this.state.memo_list[index],
            current_index: index,

            can_go_back: true,
            can_add: false,
        }, cb);
    };

    handleUpdateMemo = (memo, index) => {
        const memos = this.state.memo_list;
        this.setState({
            memo_list: memos.slice(0, index).concat([memo], memos.slice(index + 1)),
            can_go_back: false,
            can_add: true,
        })
    };

    componentDidMount() {
    }

    render() {
        return (
            <View>
                <NavigationBar
                    title={'Tiny Memo'}
                    height={44}
                    titleColor={'#fff'}
                    backgroundColor={'#FF5622'}
                    leftButtonTitle={this.state.can_go_back ? 'back' : null}
                    leftButtonTitleColor={'#fff'}
                    onLeftButtonPress={() => this.setState({redirect: '/'})}
                    rightButtonTitle={this.state.can_add ? 'Add' : null}
                    rightButtonTitleColor={'#fff'}
                    onRightButtonPress={() => this.setState({redirect: '/memo'})}
                    style={styles.navigation_bar}
                />
                <View style={{marginTop: 48}}>
                    <NativeRouter>
                        <AndroidBackButton>
                            <View style={{width: '100%', height: '100%'}}>
                                <Route exact path="/" render={(props) => (
                                    <MemoList memo_list={this.state.memo_list} onOpen={this.handleOpenMemo} />
                                )} />
                                <Route path="/memo" render={(props) => (
                                    <MemoEdit memo={this.state.current_memo} updateMemo={this.handleUpdateMemo}
                                              currentIndex={this.state.current_index} />
                                )} />
                                <Route path="/" render={() => {
                                        return this.state.redirect ? <Redirect to={this.state.redirect} /> : null
                                }}/>
                                <Route path="/memo" render={() => {
                                        return this.state.redirect ? <Redirect to={this.state.redirect} /> : null
                                }}/>
                            </View>
                        </AndroidBackButton>
                    </NativeRouter>
                </View>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    navigation_bar: {
        shadowColor: 'black',
        shadowOffset: {width: 0, height: 0},
        shadowOpacity: 1,
        shadowRadius: 5,
        elevation: 4,
    },
    title_container: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'space-around',
        alignItems: 'center',
        backgroundColor: '#FF5622',
        shadowColor: 'black',
        shadowOffset: {width: 0, height: 0},
        shadowOpacity: 1,
        shadowRadius: 5,
        elevation: 3,
    },
    title: {
        color: 'white',
        fontFamily: 'Roboto',
        fontSize: 20,
        margin: 24
    },
});

AppRegistry.registerComponent('Memo', () => App);
