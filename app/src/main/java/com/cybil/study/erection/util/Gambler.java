package com.cybil.study.erection.util;

public class Gambler {
    private int stack;
    private boolean animationIsEnd = true;

    public Gambler (int stack) {
        this.stack = stack;
    }

    public void setStack(int stack) { this.stack = stack; }
    public void addStack(int stack) { this.stack += stack; }
    public int getStack() {
        return stack;
    }

    public boolean getAnimationIsEnd(){ return this.animationIsEnd; }
    public void setAnimationIsEnd(boolean animationIsEnd) { this.animationIsEnd = animationIsEnd; }
}
