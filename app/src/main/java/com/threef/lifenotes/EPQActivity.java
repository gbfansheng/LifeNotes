package com.threef.lifenotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class EPQActivity extends AppCompatActivity {
    List<String> questionList;//EPQ问题的列表
    HashMap<String,Boolean> answerMap;//EPQ回答列表
    AppCompatTextView questionTextView;
    RadioGroup radioGroup;
    RadioButton yesButton;
    RadioButton noButton;
    AppCompatButton prevButton;
    AppCompatButton nextButton;
    int currentQuestion = -1;
    String preUrl;
    String rawResult;
    String epqResult;
    String netWorkResult;
    float nTScore;
    float eTScore;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preUrl = getIntent().getStringExtra("url");
        //装载数据？
        String[] questionArray =
                {
                        "1.你是否有广泛的爱好？"
//                        "2.在做任何事情之前，你是否都要考虑一番？",
//                        "3.你的情绪时常波动吗？",
//                        "4.当别人做了好事，而周围的人认为是你做的时候，你是否感到洋洋得意？",
//                        "5.你是一个健谈的人吗？",
//                        "6.你曾经无缘无故地觉得自己“可怜”吗？",
//                        "7.你曾经有过贪心使自己多得份外的物质利益吗?",
//                        "8.晚上你是否小心地把门锁好？",
//                        "9.你认为自己活泼吗？",
//                        "10.当你看到小孩（或动物）受折磨时是否感到难受？",
//                        "11.你是否常担心你会说出（或做出）不应该说或做的事?",
//                        "12.若你说过要做某件事，是否不管遇到什么困难都要把它做成？",
//                        "13.在愉快的聚会中你是否通常尽情享受？",
//                        "14. 你是一位易激怒的人吗？",
//                        "15.你是否有过自己做错了事反倒责备别人的时候？",
//                        "16.你喜欢会见陌生人吗？",
//                        "17.你是否相信参加储蓄是一种好办法？",
//                        "18.你的感情是否容易受到伤害？",
//                        "19.你是否服用有奇特效果或是有危险性的药物？",
//                        "20.你是否时常感到“极其厌烦”？",
//                        "21.你曾多占多得别人的东西（甚至一针一线）吗？",
//                        "22.如果条件允许，你喜欢经常外出（旅行）吗？",
//                        "23.对你所喜欢的人，你是否为取乐开过过头的玩笑？",
//                        "24.你是否常因“自罪感”而烦恼？",
//                        "25.你是否有时候谈论一些你毫无所知的事情？",
//                        "26.你是否宁愿看些书，而不想去会见别人？",
//                        "27.有坏人想要害你吗？",
//                        "28.你认为自己“神经过敏”吗？",
//                        "29.你的朋友多吗？",
//                        "30.你是个忧虑重重的人吗？",
//                        "31.你在儿童时代是否立即听从大人的吩咐而毫无怨言？",
//                        "32.你是一个无忧无虑逍遥自在的人吗？",
//                        "33.有礼貌爱整洁对你很重要吗？",
//                        "34.你是否担心将会发生可怕的事情？",
//                        "35.在结识新朋友时，你通常是主动的吗？",
//                        "36.你觉得自己是个非常敏感的人吗？",
//                        "37.和别人在一起的时候，你是否不常说话？",
//                        "38.你是否认为结婚是个框框，应该废除？",
//                        "39.你有时有点自吹自擂吗？",
//                        "40.在一个沉闷的场合，你能给大家增添生气吗？",
//                        "41.慢腾腾开车的司机是否使你讨厌？",
//                        "42.你担心自己的健康吗？",
//                        "43.你是否喜欢说笑话和谈论有趣的事情？",
//                        "44.你是否觉得大多数事情对你都是无所谓的？",
//                        "45.你小时候有过对父母鲁莽无礼的行为吗？",
//                        "46.你喜欢和别人打成一片，整天相处在一起吗？",
//                        "47.你失眠吗？",
//                        "48.你饭前必定先洗手吗？",
//                        "49.当别人问你话时，你是否对答如流？",
//                        "50.你是否宁愿有富裕时间喜欢早点动身去赴约会？",
//                        "51.你经常无缘无故感到疲倦和无精打采吗？",
//                        "52.在游戏或打牌时你曾经作弊吗？",
//                        "53.你喜欢紧张的工作吗？",
//                        "54.你时常觉得自己的生活很单调吗？",
//                        "55.你曾经为了自己而利用过别人吗？",
//                        "56.你是否参加的活动太多，已超过自己可能分配的时间？",
//                        "57.是否有那么几个人时常躲着你？",
//                        "58.你是否认为人们为保障自己的将来而精打细算、勤俭节约所费的时间太多了？",
//                        "59.你是否曾想过去死？",
//                        "60.若你确知不会被发现时，你会少付给人家钱吗？",
//                        "61.你能使一个联欢会开得成功吗？",
//                        "62.你是否尽力使自己不粗鲁？",
//                        "63.一件使你为难的事情过去之后，是否使你烦恼好久？",
//                        "64.你曾否坚持要照你的想法去办事？",
//                        "65.当你去乘火车时，你是否最后一分钟到达？",
//                        "66.你是否容易紧张？",
//                        "67.你常感到寂寞吗？",
//                        "68.你的言行总是一致吗？",
//                        "69.你有时喜欢玩弄动物吗？",
//                        "70.有人对你或你的工作吹毛求疵时，是否容易伤害你的积极性？",
//                        "71.你去赴约会或上班时，曾否迟到？",
//                        "72.你是否喜欢在你的周围有许多热闹和高兴的事？",
//                        "73.你愿意让别人怕你吗？",
//                        "74.你是否有时兴致勃勃，有时却很懒散不想动弹？",
//                        "75.你有时会把今天应该做的事拖到明天吗？",
//                        "76.别人是否认为你是生气勃勃的？",
//                        "77.别人是否对你说过许多慌话？",
//                        "78.你是否对有些事情易性急生气？",
//                        "79.若你犯有错误你是否愿意承认？",
//                        "80.你是一个整洁严谨、有条不紊的人吗？",
//                        "81.在公园里或马路上，你是否总是把果皮或废纸扔到垃圾箱里？",
//                        "82.遇到为难的事情你是否拿不定主意？",
//                        "83.你是否有过随口骂人的时候？",
//                        "84.若你乘车或坐飞机外出时，你是否担心会碰撞或出意外？",
//                        "85.你是一个爱交往的人吗？"
                };
        questionList = Arrays.asList(questionArray); //EPQ问题的列表
        answerMap = new HashMap<>();

        questionTextView = (AppCompatTextView) findViewById(R.id.question);
        radioGroup = (RadioGroup) findViewById(R.id.option_group);
        yesButton = (RadioButton) findViewById(R.id.yes);
        noButton = (RadioButton) findViewById(R.id.no);
        prevButton = (AppCompatButton) findViewById(R.id.prev);
        nextButton = (AppCompatButton) findViewById(R.id.next);

        nextQuestion();

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                answerMap.put(questionList.get(currentQuestion), checkedId == R.id.yes);
//                nextQuestion();
//            }
//        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerMap.put(questionList.get(currentQuestion), true);
                nextQuestion();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerMap.put(questionList.get(currentQuestion), false);
                nextQuestion();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevQuestion();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hint = "问题";
                Boolean isFinished = true;
                //通过计算结果
                for (int i = 0; i < questionList.size(); i++) {
                    Boolean b = answerMap.get(questionList.get(i));
                    if (b == null) {
                        hint = hint + " " + (i + 1);
                        isFinished = false;
                    }
                }
                if (isFinished) {
                    hint = "完成";
                    //计算+网络？
                    caculateEPQ();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL url;
                            String finalUrl = preUrl + "testReport" + rawResult;
                            netWorkResult = "";
                            try {
                                url = new URL(finalUrl);
                                HttpURLConnection urlConn = (HttpURLConnection) url
                                        .openConnection();  //创建一个HTTP连接
                                InputStreamReader in = new InputStreamReader(
                                        urlConn.getInputStream()); // 获得读取的内容
                                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                                String inputLine = null;
                                //通过循环逐行读取输入流中的内容
                                while ((inputLine = buffer.readLine()) != null) {
                                    netWorkResult += inputLine + "\n";
                                }
                                in.close(); //关闭字符输入流对象
                                urlConn.disconnect();   //断开连接
                            } catch (MalformedURLException e) {
                                Log.d("Collection", e.toString());
                                e.printStackTrace();
                            } catch (IOException e) {
                                Log.d("Collection", e.toString());
                                e.printStackTrace();
                            }
                            Message m = handler.obtainMessage();
                            handler.sendMessage(m); // 发送消息
                        }
                    }).start();
                } else {
                    hint = hint + "未填写";
                }
                Snackbar.make(view, hint, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //创建一个Handler对象
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (netWorkResult != null) {
                    Toast.makeText(EPQActivity.this,netWorkResult,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("epqresult",epqResult);
                    intent.putExtra("nTScore",nTScore);
                    intent.putExtra("eTScore",eTScore);
                    intent.setClass(EPQActivity.this, EPQResultActivity.class);
                    startActivity(intent);
                }
                super.handleMessage(msg);
            }
        };
    }

    private void nextQuestion() {
        if (currentQuestion >= questionList.size() - 1) {
            Toast.makeText(this,"已经是最后一题,可以提交答案",Toast.LENGTH_SHORT).show();
        } else {
            currentQuestion ++;
            questionTextView.setText(questionList.get(currentQuestion));
            Boolean b = answerMap.get(questionList.get(currentQuestion));
            if (b == null) {
                radioGroup.clearCheck();
            } else if (b == true) {
                radioGroup.check(R.id.yes);
            } else if (b == false) {
                radioGroup.check(R.id.no);
                yesButton.setChecked(false);
                noButton.setChecked(true);
            }
        }
    }

    private void prevQuestion() {
        if (currentQuestion <= 0) {
            Toast.makeText(this,"已经是第一题",Toast.LENGTH_SHORT).show();
        } else {
            currentQuestion --;
            questionTextView.setText(questionList.get(currentQuestion));
            Boolean b = answerMap.get(questionList.get(currentQuestion));
            if (b == null) {
                radioGroup.clearCheck();
            } else if (b == true) {
                radioGroup.check(R.id.yes);
            } else if (b == false) {
                radioGroup.check(R.id.no);
            }
        }
    }

    private void caculateEPQ(){
        double pTScore;
        double eTScore;
        double nTScore;
        double lTScore;
        int pScore = 0;
        int eScore = 0;
        int nScore = 0;
        int lScore = 0;
        HashSet<Integer> pTrueSet;
        HashSet<Integer> pFalseSet;
        HashSet<Integer> eTrueSet;
        HashSet<Integer> eFalseSet;
        HashSet<Integer> nTrueSet;
        HashSet<Integer> nFalseSet;
        HashSet<Integer> lTrueSet;
        HashSet<Integer> lFalseSet;
        Integer[] ptarr = {19,23,27,38,41,44,57,58,65,69,73,77};
        pTrueSet = new HashSet<>(Arrays.asList(ptarr));
        Integer[] pfarr = {2,8,10,17,33,50,62,80};
        pFalseSet = new HashSet<>(Arrays.asList(pfarr));
        Integer[] etarr = {1,5,9,13,16,22,29,32,35,40,43,46,49,53,56,61,72,76,85};
        eTrueSet = new HashSet<>(Arrays.asList(etarr));
        Integer[] efarr = {26,37};
        eFalseSet = new HashSet<>(Arrays.asList(efarr));
        Integer[] ntarr = {3,6,11,14,18,20,24,28,30,34,36,42,47,51,54,59,63,66,67,74,78,82,84};
        nTrueSet = new HashSet<>(Arrays.asList(ntarr));
        Integer[] nfarr = {-1};
        nFalseSet = new HashSet<>(Arrays.asList(nfarr));
        Integer[] ltarr = {12,31,48,68,79,81};
        lTrueSet = new HashSet<>(Arrays.asList(ltarr));
        Integer[] lfarr = {4,7,15,21,25,39,45,52,55,60,64,71,75,83};
        lFalseSet = new HashSet<>(Arrays.asList(lfarr));

        DecimalFormat format = new DecimalFormat(".00");
        for (Integer i = 0; i < questionList.size() ; i++ ) {
            Boolean bool = answerMap.get(questionList.get(i));
            if (bool) {
                if (pTrueSet.contains(i)) {
                    pScore ++;
                } else if (pFalseSet.contains(i)) {
                    pScore --;
                } else if (eTrueSet.contains(i)) {
                    eScore ++;
                } else if (eFalseSet.contains(i)) {
                    eScore --;
                } else if (nTrueSet.contains(i)) {
                    nScore ++;
                } else if (nFalseSet.contains(i)) {
                    nScore --;
                } else if (lTrueSet.contains(i)) {
                    lScore ++;
                } else if (lFalseSet.contains(i)) {
                    lScore --;
                }
            } else {
                if (pTrueSet.contains(i)) {
                    pScore--;
                } else if (pFalseSet.contains(i)) {
                    pScore++;
                } else if (eTrueSet.contains(i)) {
                    eScore--;
                } else if (eFalseSet.contains(i)) {
                    eScore++;
                } else if (nTrueSet.contains(i)) {
                    nScore--;
                } else if (nFalseSet.contains(i)) {
                    nScore++;
                } else if (lTrueSet.contains(i)) {
                    lScore--;
                } else if (lFalseSet.contains(i)) {
                    lScore++;
                }
            }
        }

        rawResult = pScore+"_"+eScore+"_"+nScore+"_"+lScore;
        //标准分在40-60分之间大约包括68.46%的常模群体，如果某个被试的标准分大于 61.5 或小于38.5，就可以认为该被是者在某量表上具有高分或低分的特征。
        //所以T分在43.3—56.7之间为中间型；在38.5—43.3或56.7—61.5之间为倾向型；在38.5以下或61.5以上为典型型
        String result = "";
        pTScore = 50 + 10 * ( pScore - 2.73) / 2.05;
        eTScore = 50 + 10 * ( pScore - 7.50) / 2.84;
        this.eTScore = (float)eTScore;
        nTScore = 50 + 10 * ( pScore - 4.42) / 2.59;
        this.nTScore = (float)nTScore;
        lTScore = 50 + 10 * ( pScore - 6.19) / 2.96;

        if (pTScore <= 38.5) {
            result = "P低分典型";
        } else if (pTScore > 38.5 && pTScore <= 43.3) {
            result = "P低分倾向";
        } else if (pTScore > 43.3 && pTScore <= 56.7) {
            result = "P中间";
        } else if (pTScore > 56.7 && pTScore <= 61.5) {
            result = "P高分倾向";
        } else if (pTScore > 61.5) {
            result = "P高分典型";
        }
        result = result + format.format(pTScore);
        if (eTScore <= 38.5) {
            result = result + "\n" + "E低分典型";
        } else if (eTScore > 38.5 && eTScore <= 43.3) {
            result = result + "\n" + "E低分倾向";
        } else if (eTScore > 43.3 && eTScore <= 56.7) {
            result = result + "\n" + "E中间";
        } else if (eTScore > 56.7 && eTScore <= 61.5) {
            result = result + "\n" + "E高分倾向";
        } else if (eTScore > 61.5) {
            result = result + "\n" + "E高分典型";
        }
        result = result + format.format(eTScore);

        if (nTScore <= 38.5) {
            result = result + "\n" + "N低分典型";
        } else if (nTScore > 38.5 && nTScore <= 43.3) {
            result = result + "\n" + "N低分倾向";
        } else if (nTScore > 43.3 && nTScore <= 56.7) {
            result = result + "\n" + "N中间";
        } else if (nTScore > 56.7 && nTScore <= 61.5) {
            result = result + "\n" + "N高分倾向";
        } else if (nTScore > 61.5) {
            result = result + "\n" + "N高分典型";
        }
        result = result + format.format(nTScore);

        if (lTScore <= 38.5) {
            result = result + "\n" + "L低分典型";
        } else if (lTScore > 38.5 && lTScore <= 43.3) {
            result = result + "\n" + "L低分倾向";
        } else if (lTScore > 43.3 && lTScore <= 56.7) {
            result = result + "\n" + "L中间";
        } else if (lTScore > 56.7 && lTScore <= 61.5) {
            result = result + "\n" + "L高分典型";
        } else if (lTScore > 61.5) {
            result = result + "\n" + "L高分倾向";
        }
        result = result + format.format(lTScore);
        epqResult = result;
//        Toast.makeText(this,"pScore="+pScore+" eScore="+eScore+" nScore="+nScore+" lScore="+lScore + "\n" + result,Toast.LENGTH_SHORT).show();
//        Log.d("EPQ","pScore="+pScore+" eScore="+eScore+" nScore="+nScore+" lScore="+lScore);
    }
}
