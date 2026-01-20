package com.cryptosignals.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cryptosignals.app.R
import com.cryptosignals.app.data.Signal
import com.cryptosignals.app.databinding.ItemSignalBinding
import android.graphics.Color

class SignalAdapter : RecyclerView.Adapter<SignalAdapter.SignalViewHolder>() {

    private val signals = mutableListOf<Signal>()

    fun setSignals(newSignals: List<Signal>) {
        signals.clear()
        signals.addAll(newSignals)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalViewHolder {
        val binding = ItemSignalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SignalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SignalViewHolder, position: Int) {
        holder.bind(signals[position])
    }

    override fun getItemCount() = signals.size

    class SignalViewHolder(private val binding: ItemSignalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(signal: Signal) {
            binding.tvSymbol.text = signal.symbol
            binding.tvTimeframe.text = "(${signal.timeframe})"
            binding.tvScore.text = "Score: ${signal.score}"
            binding.tvRating.text = signal.rating
            binding.tvDate.text = signal.createdAt
            
            binding.tvReasons.text = signal.reasons.joinToString("\n") { "• $it" }

            val context = binding.root.context
            val ratingColor = when {
                signal.rating.contains("BUY") -> context.getColor(R.color.score_high)
                signal.rating.contains("SELL") -> context.getColor(R.color.score_low)
                else -> context.getColor(R.color.score_medium)
            }
            binding.tvRating.setTextColor(Color.WHITE)
            binding.tvRating.setBackgroundColor(ratingColor)
        }
    }
}
