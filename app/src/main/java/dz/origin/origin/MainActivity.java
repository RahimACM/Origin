package dz.origin.origin;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.BinaryOperator;

import static org.web3j.tx.Transfer.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import rx.functions.Action0;

public class MainActivity extends AppCompatActivity {

    private final int splashTime = 2000;
    public void test() throws Exception {

        Web3j web3j = Web3j.build(new HttpService(
                "http://192..0.0.1:7000"));
        String version = web3j.web3ClientVersion().send().getWeb3ClientVersion();
        System.out.println("Version : "+version);
        Credentials credentials = Credentials.create("7e232b9b5b5f64da32f796483b4590bf4fd8a0643e5c7f96f957913282de3f77");;
        System.out.println(credentials.getAddress());
        System.out.println("Sending 1 Wei ("
                + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
        MainContract contract = MainContract.deploy(
                web3j, credentials,
                new BigInteger("20000000000"),
                BigInteger.valueOf(6721975)).send();
        contract.logNewUserEventObservable(new EthFilter()).doOnCompleted(new Action0() {
            @Override
            public void call() {
                System.out.println(this.toString());
            }
        });
//        TransactionReceipt t = contract.insertUser("","","", BigInteger.valueOf(20));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        },splashTime);
    }
}
