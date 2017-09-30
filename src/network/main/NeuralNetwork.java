package network.main;

import java.util.Random;

public class NeuralNetwork {
	private int[] layers;
	private float learningRate;
	private NeuronLayer[] neuronLayer;
	private Random r;
	
	public NeuralNetwork(int[] layers, float learningRate){
		this.layers =  new int[layers.length];
		this.learningRate = learningRate;
		for(int i = 0; i < layers.length; i++){
			this.layers[i] = layers[i];
		}
		
		r = new Random();
		initLayer();
	}
	
	private void initLayer(){
		neuronLayer = new NeuronLayer[layers.length];
		neuronLayer[0] = new NeuronLayer(layers[0], layers[1], NeuralStatus.Input, learningRate);
		neuronLayer[layers.length - 1] = new NeuronLayer(layers[layers.length - 1], 0, NeuralStatus.Output, learningRate);
		
		for(int i = 1; i < layers.length - 1; i++){
			 neuronLayer[i] = new NeuronLayer(layers[i], layers[i + 1], NeuralStatus.Hidden, learningRate);
		}
	}

	public NeuronLayer getOutputLayer(){
		return neuronLayer[neuronLayer.length - 1];
	}
	
	public NeuronLayer FeedForward(Neuron[] inputs){
		neuronLayer[0].FeedForward(inputs);
		
		for(int i = 1; i < neuronLayer.length; i++){
			neuronLayer[i].FeedForward(neuronLayer[i - 1].getOutput());
		}
		
		return neuronLayer[neuronLayer.length - 1];
	}

	public void mutate(float chance){
		for(int i = 0; i < neuronLayer.length - 1; i++){
			neuronLayer[i].mutate(chance);
		}
	}

	public void backProp(float[] expected){
		neuronLayer[neuronLayer.length - 2].backPropInitial(expected);
		for(int i = neuronLayer.length - 3; i >= 0; i--){
			neuronLayer[i].backPropHidden(neuronLayer[i + 1]);
		}
	}
}