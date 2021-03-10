import { CallbackID } from '@capacitor/core/dist/esm/core-plugin-definitions';

declare module '@capacitor/core' {
  interface PluginRegistry {
    CapacitorJobScheduler: CapacitorJobSchedulerPlugin;
  }
}

export interface CallbackError extends Error {
  code?: string;
}

export interface CapacitorJobSchedulerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  scheduleJob(callback: (error?: CallbackError) => void): CallbackID;
  cancelJob(): Promise<void>;
}
