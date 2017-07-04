/**
 * Created by Think on 2017/7/4.
 */
import React, { Component } from 'react';
import {
    StyleSheet,
    Text,
    TextInput,
    ScrollView,
    View,
    TouchableNativeFeedback,
    Dimensions,
} from 'react-native';

import { NativeRouter, Route, Link, AndroidBackButton, Redirect } from 'react-router-native';
import { Avatar, Button, Divider, Card } from 'react-native-material-design';

import { NavigationActions } from 'react-navigation';

export default class MemoEdit extends Component {
    static navigationOptions = {
        title: 'Tiny Memo',
    };

    constructor(props) {
        super(props);

        const { title, content, time } = this.props.navigation.state.params.memo;
        const index = this.props.navigation.state.params.index;

        this.state = {
            title: title,
            content: content,
            time: new Date(),
            index: index
        }
    }

    handleRedirect = () => {


        const navigateAction = NavigationActions.navigate({

            routeName: 'Index',

            params: {
                memo: this.state,
                index: this.state.index
            },

            action: NavigationActions.navigate({ routeName: 'index'})
        });

        this.props.navigation.dispatch(navigateAction);

        // this.props.navigation.navigate('Index', {}, {
        //     memo: {
        //         title: this.state.title,
        //         content: this.state.content,
        //         time: new Date()
        //     },
        //     index: this.state.index
        // })
    };

    handleDelete = () => {

    };

    render() {
        return (
            <View style={styles.container}>
                <ScrollView>
                    <View style={styles.memo__title__container}>
                        <Text style={styles.memo__title}>Title:</Text>
                        <TextInput
                            autoFocus={true}
                            style={{height: 40, flex: 1}}
                            onChangeText={(title) => this.setState({title})}
                            value={this.state.title}
                        />
                    </View>
                    <View style={styles.memo__content__container}>
                        <View>
                            <Text style={styles.memo__content}>Content: </Text>
                            <TextInput
                                style={{height: 80}}
                                onChangeText={(content) => this.setState({content})}
                                value={this.state.content}
                                multiline={true}
                                maxLength={1024}
                            />
                        </View>
                        <Text>
                            Last Updated Time: {this.state.time.toLocaleString()}
                        </Text>
                    </View>
                    <Button text="删除" overrides={{
                        textColor: '#ffffff',
                        backgroundColor: '#00BCD4',
                        rippleColor: '#333333'
                    }} raised={true} value="delete" onPress={() => this.handleDelete()}/>
                    <Button text="保存" overrides={{
                        textColor: '#ffffff',
                        backgroundColor: '#00BCD4',
                        rippleColor: '#333333'
                    }} raised={true} value="save" onPress={() => this.handleRedirect()}/>
                </ScrollView>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        marginTop: 32,
        marginLeft: 16,
    },
    memo__title__container: {
        alignItems: 'center',
        flexDirection: 'row',
    },
    memo__title: {
        color: 'black',
    },
    memo__content__container: {
        justifyContent: 'space-between',
    },
    memo__content: {
        color: 'black',
    },
    memo__time: {
        fontSize: 14
    }
});